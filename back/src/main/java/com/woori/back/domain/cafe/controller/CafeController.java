package com.woori.back.domain.cafe.controller;

import com.woori.back.domain.cafe.dto.*;
import com.woori.back.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes")
public class CafeController {

    private final CafeService cafeService;

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CafeCreateResponse> createCafe(@AuthenticationPrincipal Long memberId, @RequestBody CafeCreateRequest request) {
        CafeCreateResponse response = cafeService.createCafe(memberId, request);

        Long cafeId = response.getId();
        String name = response.getName();
        log.info("생성된 카페 id: {}, name: {}", cafeId, name);

        URI location = URI.create("/api/cafes/" + cafeId);

        return ResponseEntity.created(location).body(response);
    }

    // 내 카페 목록 조회
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/me")
    public ResponseEntity<Page<CafesResponse>> getMyCafes(@AuthenticationPrincipal Long memberId, Pageable pageable) {
        Page<CafesResponse> response = cafeService.getMyCafes(memberId, pageable);

        return ResponseEntity.ok().body(response);
    }

    // 카페 단일 조회
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeResponse> getCafe(@PathVariable Long cafeId) {
        CafeResponse response = cafeService.getCafeInfo(cafeId);

        return ResponseEntity.ok().body(response);
    }

    // 카페 목록 조회
    @GetMapping
    public ResponseEntity<Page<CafesResponse>> getCafes(Pageable pageable) {
        Page<CafesResponse> response = cafeService.getCafes(pageable);

        return ResponseEntity.ok().body(response);
    }

    // 카페 수정
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PatchMapping("/{cafeId}")
    public ResponseEntity<CafeResponse> updateCafeInfo(@PathVariable Long cafeId, @RequestBody CafeUpdateRequest request) {
        CafeResponse response = cafeService.updateCafeInfo(cafeId, request);

        return ResponseEntity.ok().body(response);
    }

    // 카페 삭제
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping("/{cafeId}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long cafeId) {
        cafeService.deleteCafe(cafeId);

        return ResponseEntity.noContent().build();
    }

    // 카페 운영 시작
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/{cafeId}/open")
    public ResponseEntity<CafeResponse> openCafe(@PathVariable Long cafeId) {
        CafeResponse response = cafeService.openCafe(cafeId);

        return ResponseEntity.ok().body(response);
    }

    // 카페 운영 종료
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/{cafeId}/close")
    public ResponseEntity<CafeResponse> closedCafe(@PathVariable Long cafeId) {
        CafeResponse response = cafeService.closedCafe(cafeId);

        return ResponseEntity.ok().body(response);
    }
}