package com.woori.back.domain.coupon.repository;

import com.woori.back.domain.coupon.entity.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {

    Optional<CouponPolicy> findCouponPolicyByCafe_Id(Long cafeId);
}
