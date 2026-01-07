package com.woori.back.domain.stamp.repository;

import com.woori.back.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Long> {
}
