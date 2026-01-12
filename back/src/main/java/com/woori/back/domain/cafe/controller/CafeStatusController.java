package com.woori.back.domain.cafe.controller;

import com.woori.back.domain.cafe.dto.CafeResponse;
import com.woori.back.domain.cafe.dto.CafeStatusChangeRequest;
import com.woori.back.domain.cafe.service.CafeStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cafes/")
@RequiredArgsConstructor
public class CafeStatusController {

    private final CafeStatusService cafeStatusService;

    // 카페 운영 시작
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/{cafe-id}/open")
    public ResponseEntity<CafeResponse> openCafe(@PathVariable(name = "cafe-id") Long cafeId) {
        CafeResponse response = cafeStatusService.openCafe(cafeId);

        return ResponseEntity.ok().body(response);
    }

    // 카페 운영 종료
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/{cafe-id}/close")
    public ResponseEntity<CafeResponse> closedCafe(@PathVariable(name = "cafe-id") Long cafeId) {
        CafeResponse response = cafeStatusService.closedCafe(cafeId);

        return ResponseEntity.ok().body(response);
    }

    // 카페 준비
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/{cafe-id}/ready")
    public ResponseEntity<CafeResponse> readyCafe(@PathVariable(name = "cafe-id") Long cafeId, @RequestBody CafeStatusChangeRequest request) {
        CafeResponse response = cafeStatusService.readyCafe(cafeId);

        return ResponseEntity.ok().body(response);
    }

    // 카페 휴무
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping("/{cafe-id}/rest")
    public ResponseEntity<CafeResponse> restCafe(@PathVariable(name = "cafe-id") Long cafeId, @RequestBody CafeStatusChangeRequest request) {
        CafeResponse response = cafeStatusService.restCafe(cafeId);

        return ResponseEntity.ok().body(response);
    }

}
