package com.woori.back.domain.stamp.entity;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@Table(name = "stamp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 스탬프 식별 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 스탬프와 회원은 N:1 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe; // 스탬프와 카페는 N:1 관계

    private int amount; // 현재 스탬프 수

    private int total; // 누적 스탬프 수

    @Builder
    public Stamp(Member member, Cafe cafe, int amount, int total) {
        this.member = member;
        this.cafe = cafe;
        this.amount = amount;
        this.total = total;

        if (member != null) {
            addMember(member);
        }

        if (cafe != null) {
            addCafe(cafe);
        }
    }

    public void addMember(Member member) {
        this.member = member;
        member.getStamps().add(this);
    }

    public void addCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getStamps().add(this);
    }

}
