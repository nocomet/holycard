package com.nocomet.holycard.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.nocomet.holycard.conf.CommonCodeConf;
import com.nocomet.holycard.domain.entity.CommonCode;
import com.nocomet.holycard.domain.entity.HolyCard;
import com.nocomet.holycard.domain.repository.HolyCardRepository;
import com.nocomet.holycard.exception.ApiBaseException;
import com.nocomet.holycard.exception.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class HolyCardService {

    private final CommonCodeService commonCodeService;
    private final HolyCardRepository holyCardRepository;
    private final AmazonS3 s3Client;

    // NOTE. 나중에 인기가 많아지면 하나씩 올리는게 아니라.. queue 에 받아놓고 주기적으로 올리는 로직이 필요할 듯
    @Transactional
    public HolyCard plusHeart(Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = find(cardSeq);
        holyCard.plusNumberOfHeart();
        return holyCard;
    }

    @Transactional
    public HolyCard minusHeart(Long cardSeq) throws ApiBaseException {
        HolyCard holyCard = find(cardSeq);
        holyCard.minusNumberOfHeart();
        return holyCard;
    }

    @Cacheable(cacheNames = "cache_holy_card_find", key = "#cardSeq")
    public HolyCard find(Long cardSeq) throws ApiBaseException {
        return holyCardRepository.findById(cardSeq).orElseThrow(()
            -> new ApiBaseException(ApiError.NOT_FOUND, String.format("HolyCard(cardSeq=%d) is not found.", cardSeq)));
    }

    @Cacheable(cacheNames = "cache_holy_card_find_list", key = "#pageable")
    public Page<HolyCard> getCardList(Pageable pageable) {
        Page<HolyCard> all = holyCardRepository.findAll(pageable);
        return all;
    }

    public HolyCard saveCard(String filename, String content, MultipartFile imageFile) {
        String bucketName = commonCodeService.getS3BucketName();
        try {
            // 임시 파일 생성
            File imageTmpFile = File.createTempFile("holycard", filename);
            imageTmpFile.deleteOnExit();

            // request 로 받은 multipart file 을 file 로 변환
            InputStream fileStream = imageFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, imageTmpFile);

            // s3 upload
            s3Client.putObject(bucketName, filename, imageTmpFile);

            HolyCard holyCard = new HolyCard(filename, content);
            return holyCardRepository.save(holyCard);
        } catch (IOException e) {
            log.error("saveCard IOException e:{}", ExceptionUtils.getStackTrace(e));
        } catch (Exception e) {
            log.error("saveCard Exception e:{}", ExceptionUtils.getStackTrace(e));
        }

        return null;
    }

    @Cacheable(cacheNames = "cache_card_image", key = "#imageName")
    public byte[] getImage(String imageName) {
        log.info("getImage not cache. " + imageName);
        String bucketName = commonCodeService.getS3BucketName();
        S3Object object = s3Client.getObject(bucketName, imageName);

        try {
            InputStream in = object.getObjectContent();
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            log.error("getImage IOException e:{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public String getImageUrlBase() {
        CommonCode commonCode = commonCodeService.find(CommonCodeConf.IMAGE_URL_BASE.name());
        if (commonCode == null) {
            commonCodeService.createIfNull(CommonCodeConf.IMAGE_URL_BASE.name(), "http://127.0.0.1:8080/apis/v1/holy-card/{cardSeq}/image.jpg");
            return "http://127.0.0.1:8080/apis/v1/holy-card/{cardSeq}/image.jpg";
        }

        return commonCode.getValue();
    }
}
