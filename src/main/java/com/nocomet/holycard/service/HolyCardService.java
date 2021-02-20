package com.nocomet.holycard.service;

import com.nocomet.holycard.domain.entity.HolyCard;
import com.nocomet.holycard.domain.repository.HolyCardRepository;
import com.nocomet.holycard.exception.ApiBaseException;
import com.nocomet.holycard.exception.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class HolyCardService {

    private final HolyCardRepository holyCardRepository;


    public HolyCard save(String content) {
        String cardImageUrl = "test"; // todo S3 에 올리고 얻는 결과를 리턴 받아야 함.
        HolyCard holyCard = new HolyCard(cardImageUrl, content);
        HolyCard save = holyCardRepository.save(holyCard);
        return save;
    }

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
}
