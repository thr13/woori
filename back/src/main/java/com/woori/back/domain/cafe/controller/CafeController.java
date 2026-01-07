package com.woori.back.domain.cafe.controller;

import com.woori.back.domain.cafe.dto.CafeCreateRequest;
import com.woori.back.domain.cafe.dto.CafeCreateResponse;
import com.woori.back.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
