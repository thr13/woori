package com.woori.back.domain.stamp.repository;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    @Query("UPDATE Stamp s SET s.amount = s.amount + :amount WHERE s.cafe =:cafe AND s.member =:member")
    int updateStamp(@Param("cafe") Cafe cafe, @Param("member") Member member, @Param("amount") int amount);

}
