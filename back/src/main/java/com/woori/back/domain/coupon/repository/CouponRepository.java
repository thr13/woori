package com.woori.back.domain.coupon.repository;

import com.woori.back.domain.coupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Page<Coupon> findAll(Pageable pageable);

    Page<Coupon> findCouponByMember_Id(Long memberId, Pageable pageable);
}
