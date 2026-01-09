package com.woori.back.domain.stamp.repository;

import com.woori.back.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    @Modifying
    @Query("UPDATE Stamp s SET s.amount = s.amount + :amount, s.total = s.total + :amount WHERE s.id =: stampId")
    void accumulationAmount(@Param("stampId") Long stampId, @Param("amount") int amount);

}
