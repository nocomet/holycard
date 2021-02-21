package com.nocomet.holycard.service;

import com.nocomet.holycard.conf.CommonCodeConf;
import com.nocomet.holycard.domain.entity.CommonCode;
import com.nocomet.holycard.domain.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    public void save(String commonKey, String value, String description) {
        if (commonKey == null) {
            return ;
        }

        CommonCode commonCode = new CommonCode(commonKey, value, description);
        commonCodeRepository.save(commonCode);
    }

    public List<CommonCode> findAll() {
        return commonCodeRepository.findAll();
    }

    public CommonCode find(String commonKey) {
        return commonCodeRepository.findById(commonKey).orElse(null);
    }

    @Transactional
    public CommonCode updateValue(String commonKey, String value) {
        CommonCode commonCode = find(commonKey);
        commonCode.setValue(value);
        return commonCodeRepository.save(commonCode);
    }

    @Transactional
    public void deleteValue(String key) {
        CommonCode commonCode = find(key);
        commonCodeRepository.delete(commonCode);
    }

    @Transactional
    public String getS3BucketName() {
        CommonCode commonCode = find(CommonCodeConf.S3_BUCKET_NAME.name());
        if (commonCode == null) {
            createIfNull(CommonCodeConf.S3_BUCKET_NAME.name(), "holycard");
            return "holycard";
        }

        return commonCode.getValue();
    }

    private void createIfNull(String commonKey, String value) {
        save(commonKey, value, "createIfNull");
    }
}
