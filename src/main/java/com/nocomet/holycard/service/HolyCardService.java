package com.nocomet.holycard.service;

import com.amazonaws.services.s3.AmazonS3;
import com.nocomet.holycard.domain.entity.HolyCard;
import com.nocomet.holycard.domain.repository.HolyCardRepository;
import com.nocomet.holycard.exception.ApiBaseException;
import com.nocomet.holycard.exception.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


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

    public HolyCard find(Long cardSeq) throws ApiBaseException {
        return holyCardRepository.findById(cardSeq).orElseThrow(()
            -> new ApiBaseException(ApiError.NOT_FOUND, String.format("HolyCard(cardSeq=%d) is not found.", cardSeq)));
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
}
