package com.woori.back.domain.coupon.entity;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "coupon_policy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponPolicy extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 쿠폰정책 식별번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe; // 쿠폰정책과 카페는 1:N 관계

    @OneToMany(mappedBy = "coupon_policy")
    private final List<Coupon> coupons = new ArrayList<>(); // 쿠폰정책과 쿠폰은 N:1 관계

    private String reward; // 쿠폰 보상

    @Column(name = "stamp_num")
    private int stampNum; // 교환 스탬프 수

    @Builder
    public CouponPolicy(Cafe cafe, String reward, int stampNum) {
        this.cafe = cafe;
        this.reward = reward;
        this.stampNum = stampNum;

        if (cafe != null) {
            addCafe(cafe);
        }
    }

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getCouponPolicies().add(this);
    }

    // todo: 비즈니스 로직(도메인 책임) 추가
    public void update(String reward, int stampNum) {
        if (StringUtils.hasText(reward)) {
            this.reward = reward;
        }

        if (stampNum >= 0) {
            this.stampNum = stampNum;
        }
    }
}
