package com.woori.back.domain.cafe.service;

import com.woori.back.domain.cafe.dto.CafeResponse;
import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.exception.NotFoundCafeException;
import com.woori.back.domain.cafe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CafeStatusService {

    private final CafeRepository cafeRepository;

    // 카페 운영 시작
    @Transactional
    public CafeResponse openCafe(Long cafeId) {
        log.info("카페 운영 시작");
        Cafe cafe = findCafeById(cafeId);
        cafe.open();

        return CafeResponse.from(cafe);
    }

    // 카페 운영 종료
    @Transactional
    public CafeResponse closedCafe(Long cafeId) {
        log.info("카페 운영 종료");
        Cafe cafe = findCafeById(cafeId);
        cafe.closed();

        return CafeResponse.from(cafe);
    }

    // 카페 영업 준비
    @Transactional
    public CafeResponse readyCafe(Long cafeId) {
        log.info("카페 운영 준비");
        Cafe cafe = findCafeById(cafeId);
        cafe.ready();

        return CafeResponse.from(cafe);
    }

    // 카페 휴무
    @Transactional
    public CafeResponse restCafe(Long cafeId) {
        log.info("카페 휴무");
        Cafe cafe = findCafeById(cafeId);
        cafe.rest();

        return CafeResponse.from(cafe);
    }

    private Cafe findCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(
                        () -> new NotFoundCafeException("Not found cafe by id: " + cafeId)
                );
    }

}
