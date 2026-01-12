package com.woori.back.domain.coupon.entity;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicy couponPolicy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "coupon_status")
    private CouponStatus couponStatus;

    @Column(name = "code")
    private String code;

    @Builder
    public Coupon(CouponPolicy couponPolicy, Member member, Cafe cafe, CouponStatus couponStatus, String code) {
        this.couponPolicy = couponPolicy;
        this.member = member;
        this.cafe = cafe;
        this.couponStatus = couponStatus;
        this.code = code;

        if (couponPolicy != null) {
            addCouponPolicy(couponPolicy);
        }

        if (member != null) {
            addMember(member);
        }

        if (cafe != null) {
            addCafe(cafe);
        }
    }

    public void addCouponPolicy(CouponPolicy couponPolicy) {
        this.couponPolicy = couponPolicy;
        couponPolicy.getCoupons().add(this);
    }

    public void addMember(Member member) {
        this.member = member;
        member.getCoupons().add(this);
    }

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getCoupons().add(this);
    }

    public void update(CouponStatus couponStatus) {
        this.couponStatus = couponStatus;
    }
}
