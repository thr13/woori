package com.woori.back.domain.coupon.controller;

import com.woori.back.domain.coupon.dto.CouponCreateRequest;
import com.woori.back.domain.coupon.dto.CouponCreateResponse;
import com.woori.back.domain.coupon.dto.CouponResponse;
import com.woori.back.domain.coupon.dto.CouponUpdateRequest;
import com.woori.back.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // 쿠폰 생성
    @PostMapping
    public ResponseEntity<CouponCreateResponse> createCoupon(@RequestBody CouponCreateRequest request) {
        CouponCreateResponse response = couponService.createCoupon(request);

        URI location = URI.create("/api/coupon/" + response.getId());

        return ResponseEntity.created(location).body(response);
    }

    // 쿠폰 조회(단일)
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> getCoupon(@PathVariable Long couponId) {
        CouponResponse response = couponService.getCouponInfo(couponId);

        return ResponseEntity.ok().body(response);
    }

    // 쿠폰 목록 조회
    @GetMapping
    public ResponseEntity<Page<CouponResponse>> getCoupons(Pageable pageable) {
        Page<CouponResponse> response = couponService.getCoupons(pageable);

        return ResponseEntity.ok().body(response);
    }

    // 쿠폰 업데이트
    @PatchMapping("/{couponId}")
    public ResponseEntity<CouponResponse> updateCouponInfo(@PathVariable Long couponId, @RequestBody CouponUpdateRequest request) {
        CouponResponse response = couponService.updateCouponInfo(couponId, request);

        return ResponseEntity.ok().body(response);
    }

    // 쿠폰 삭제
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);

        return ResponseEntity.noContent().build();
    }

    // 자신이 보유한 쿠폰 목록 조회
    @GetMapping("/me")
    public ResponseEntity<Page<CouponResponse>> getMyCoupons(@AuthenticationPrincipal Long memberId, Pageable pageable) {
        Page<CouponResponse> response = couponService.getMyCoupons(memberId, pageable);

        return ResponseEntity.ok().body(response);
    }

}
