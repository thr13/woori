package com.woori.back.domain.coupon.repository;

import com.woori.back.domain.coupon.entity.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {

}
