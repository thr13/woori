package com.woori.back.domain.coupon.controller;

import com.woori.back.domain.coupon.dto.CouponPolicyCreateRequest;
import com.woori.back.domain.coupon.dto.CouponPolicyCreateResponse;
import com.woori.back.domain.coupon.dto.CouponPolicyResponse;
import com.woori.back.domain.coupon.dto.CouponPolicyUpdateRequest;
import com.woori.back.domain.coupon.service.CouponPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon-policies")
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    // 쿠폰정책 생성
    @PostMapping
    public ResponseEntity<CouponPolicyCreateResponse> createCouponPolicy(@RequestBody CouponPolicyCreateRequest request) {
        CouponPolicyCreateResponse response = couponPolicyService.createCouponPolicy(request);

        URI location = URI.create("/api/coupon-policies/" + response.getId());

        return ResponseEntity.created(location).body(response);
    }

    // 쿠폰정책 조회(단일)
    @GetMapping("/{couponPolicyId}")
    public ResponseEntity<CouponPolicyResponse> getCouponPolicyInfo(@PathVariable Long couponPolicyId) {
        CouponPolicyResponse response = couponPolicyService.getCouponPolicyInfo(couponPolicyId);

        return ResponseEntity.ok().body(response);
    }

    // 쿠폰정책 전체 조회
    @GetMapping
    public ResponseEntity<Page<CouponPolicyResponse>> getCouponPolicies(Pageable pageable) {
        Page<CouponPolicyResponse> response = couponPolicyService.getCouponPolicies(pageable);

        return ResponseEntity.ok().body(response);
    }

    // 쿠폰정책 수정
    @PatchMapping("/{couponPolicyId}")
    public ResponseEntity<CouponPolicyResponse> updateCouponPolicyInfo(@PathVariable Long couponPolicyId, @RequestBody CouponPolicyUpdateRequest request) {
        CouponPolicyResponse response = couponPolicyService.updateCouponPolicyInfo(couponPolicyId, request);

        return ResponseEntity.ok().body(response);
    }


    // 쿠폰정책 삭제
    @DeleteMapping("/{couponPolicyId}")
    public ResponseEntity<Void> deleteCouponPolicy(@PathVariable Long couponPolicyId) {
        couponPolicyService.deleteCouponPolicy(couponPolicyId);

        return ResponseEntity.noContent().build();
    }
}
