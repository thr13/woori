package com.woori.back.domain.cafe.entity;

import com.woori.back.domain.coupon.entity.Coupon;
import com.woori.back.domain.coupon.entity.CouponPolicy;
import com.woori.back.domain.member.entity.Member;
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
@Table(name = "cafe")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long id; // 카페 식별번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원과 카페는 1:N 관계

    @OneToMany(mappedBy = "cafe")
    private final List<Coupon> coupons = new ArrayList<>(); // 카페와 쿠폰은 N:1 관계

    @OneToMany(mappedBy = "cafe")
    private final List<CouponPolicy> couponPolicies = new ArrayList<>(); // 카페와 쿠폰 정책은 N:1 관계

    @Column(nullable = false)
    private String name; // 가게명

    @Column(nullable = false, name = "business_registration_no", unique = true)
    private String businessRegistrationNo; // 사업자 등록번호

    @Column(nullable = true, length = 500)
    private String introduction; // 가게 소개글

    @Column(nullable = false)
    private String phone; // 전화번호

    @Column(name = "image_url", nullable = true)
    private String imageUrl; // 가게 이미지

    @Enumerated(EnumType.STRING)
    @Column(name = "cafe_status", nullable = false)
    private CafeStatus cafeStatus; // 가게 상태

    @Embedded
    private Address address;

    @Builder
    public Cafe(Member member, String name, String businessRegistrationNo, String introduction, String phone, String imageUrl, Address address) {
        this.member = member;
        this.name = name;
        this.businessRegistrationNo = businessRegistrationNo;
        this.introduction = introduction;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.cafeStatus = CafeStatus.CLOSED;
        this.address = address;

        if (member != null) {
            addMember(member);
        }
    }

    public void addMember(Member member) {
        this.member = member; // cafe -> member
        member.getCafes().add(this); // member -> cafe
    }

    public void changeStatus(CafeStatus cafeStatus) {
        this.cafeStatus = cafeStatus;
    }

    public void open() {
        changeStatus(CafeStatus.OPEN);
    }

    public void closed() {
        changeStatus(CafeStatus.CLOSED);
    }

    public void updateInfo(String name, String introduction, String phone, String imageUrl, Address address) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }

        if (StringUtils.hasText(introduction)) {
            this.introduction = introduction;
        }

        if (StringUtils.hasText(phone)) {
            this.phone = phone;
        }

        if (StringUtils.hasText(imageUrl)) {
            this.imageUrl = imageUrl;
        }

        if (address != null) {
            this.address = address;
        }
    }
}