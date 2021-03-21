package com.nocomet.holycard.service;

import com.nocomet.holycard.conf.CommonCodeConf;
import com.nocomet.holycard.domain.entity.CommonCode;
import com.nocomet.holycard.domain.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    public CommonCode save(String commonKey, String value, String description) {
        if (commonKey == null) {
            return null;
        }

        CommonCode commonCode = new CommonCode(commonKey, value, description);
        return commonCodeRepository.save(commonCode);
    }

    public List<CommonCode> findAll() {
        return commonCodeRepository.findAll();
    }

    @Cacheable(cacheNames = "cache_common_code_find", key = "#commonKey")
    public CommonCode find(String commonKey) {
        return commonCodeRepository.findById(commonKey).orElse(null);
    }

    public CommonCode findNoCache(String commonKey) {
        return commonCodeRepository.findById(commonKey).orElse(null);
    }

    @Transactional
    public CommonCode updateValue(String commonKey, String value, String description) {
        CommonCode commonCode = findNoCache(commonKey);
        commonCode.setValue(value);
        if (description != null) {
            commonCode.setDescription(description);
        }
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

    public void createIfNull(String commonKey, String value) {
        save(commonKey, value, "createIfNull");
    }
}
