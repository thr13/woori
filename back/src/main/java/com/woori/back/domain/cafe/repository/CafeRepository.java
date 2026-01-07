package com.woori.back.domain.cafe.repository;

import com.woori.back.domain.cafe.entity.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    Optional<Cafe> findById(Long id);

    Page<Cafe> findByMember_Id(Long memberId, Pageable pageable);
}