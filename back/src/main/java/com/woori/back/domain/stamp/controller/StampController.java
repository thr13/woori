package com.woori.back.domain.stamp.controller;

import com.woori.back.domain.stamp.dto.StampCreateRequest;
import com.woori.back.domain.stamp.dto.StampCreateResponse;
import com.woori.back.domain.stamp.dto.StampResponse;
import com.woori.back.domain.stamp.service.StampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/stamps")
@RequiredArgsConstructor
public class StampController {

    private final StampService stampService;

    // 스탬프 생성
    @PostMapping
    public ResponseEntity<StampCreateResponse> createStamp(StampCreateRequest request) {
        StampCreateResponse response = stampService.createStamp(request);

        URI location = URI.create("/api/coupons/" + response.getStampId());

        return ResponseEntity.created(location).body(response);
    }

    // 스탬프 조회(단일)
    @GetMapping("/{stampId}")
    public ResponseEntity<StampResponse> getStampInfo(@PathVariable Long stampId) {
        StampResponse response = stampService.getStampInfo(stampId);

        return ResponseEntity.ok().body(response);
    }

    // 스탬프 목록 조회
    @GetMapping
    public ResponseEntity<Page<StampResponse>> getStampInfo(Pageable pageable) {
        Page<StampResponse> response = stampService.getStamps(pageable);

        return ResponseEntity.ok().body(response);
    }

    // 스탬프 삭제
    @DeleteMapping("/{stampId}")
    public ResponseEntity<Void> deleteStamp(@PathVariable Long stampId) {
        stampService.deleteStamp(stampId);

        return ResponseEntity.noContent().build();
    }
}
