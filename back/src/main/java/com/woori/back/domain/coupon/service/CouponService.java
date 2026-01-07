package com.woori.back.domain.coupon.service;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.exception.NotFoundCafeException;
import com.woori.back.domain.cafe.repository.CafeRepository;
import com.woori.back.domain.coupon.dto.CouponCreateRequest;
import com.woori.back.domain.coupon.dto.CouponCreateResponse;
import com.woori.back.domain.coupon.dto.CouponResponse;
import com.woori.back.domain.coupon.dto.CouponUpdateRequest;
import com.woori.back.domain.coupon.entity.Coupon;
import com.woori.back.domain.coupon.entity.CouponPolicy;
import com.woori.back.domain.coupon.entity.CouponStatus;
import com.woori.back.domain.coupon.exception.NotFoundCouponException;
import com.woori.back.domain.coupon.exception.NotFoundCouponPolicyException;
import com.woori.back.domain.coupon.repository.CouponPolicyRepository;
import com.woori.back.domain.coupon.repository.CouponRepository;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.exception.NotFoundMemberException;
import com.woori.back.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    // 쿠폰 생성
    @Transactional
    public CouponCreateResponse createCoupon(CouponCreateRequest request) {
        log.info("쿠폰 생성");

        CouponPolicy couponPolicy = findCouponPolicyById(request.getCouponPolicyId());
        Member member = findMemberById(request.getMemberId());
        Cafe cafe = findCafeById(request.getCafeId());

        Coupon coupon = Coupon.builder()
                .couponPolicy(couponPolicy)
                .member(member)
                .cafe(cafe)
                .couponStatus(CouponStatus.ACTIVE)
                .build();

        return CouponCreateResponse.from(coupon);
    }

    // 쿠폰 조회(단건)
    @Transactional(readOnly = true)
    public CouponResponse getCouponInfo(Long couponId) {
        log.info("쿠폰 단일 조회: {}", couponId);
        Coupon coupon = findCouponById(couponId);

        return CouponResponse.from(coupon);
    }

    // 쿠폰 목록 조회
    @Transactional(readOnly = true)
    public Page<CouponResponse> getCoupons(Pageable pageable) {
        log.info("쿠폰 목록 조회");

        return couponRepository.findAll(pageable)
                .map(CouponResponse::from);
    }

    // 쿠폰 수정
    @Transactional
    public CouponResponse updateCouponInfo(Long couponId, CouponUpdateRequest request) {
        log.info("쿠폰 수정: {}", couponId);
        Coupon coupon = findCouponById(couponId);
        coupon.update(request.getCouponStatus());

        return CouponResponse.from(coupon);
    }

    // 쿠폰 삭제
    @Transactional
    public void deleteCoupon(Long couponId) {
        log.info("쿠폰 삭제: {}", couponId);
        Coupon coupon = findCouponById(couponId);

        couponRepository.delete(coupon);
    }

    // 내가 가진 쿠폰 목록 조회
    @Transactional(readOnly = true)
    public Page<CouponResponse> getMyCoupons(Long memberId, Pageable pageable) {
        log.info("내 쿠폰 목록 조회: {}", memberId);

        return couponRepository.findCouponByMember_Id(memberId, pageable)
                .map(CouponResponse::from);
    }

    public Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(
                        () -> new NotFoundCouponException("Not found coupon by id: " + couponId)
                );
    }

    public CouponPolicy findCouponPolicyById(Long couponPolicyId) {
        return couponPolicyRepository.findById(couponPolicyId)
                .orElseThrow(
                        () -> new NotFoundCouponPolicyException("Not found coupon policy by id: " + couponPolicyId)
                );
    }

    public Cafe findCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(
                        () -> new NotFoundCafeException("Not found cafe by id: " + cafeId)
                );
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotFoundMemberException("Not found member by id: " + memberId)
                );
    }

}
