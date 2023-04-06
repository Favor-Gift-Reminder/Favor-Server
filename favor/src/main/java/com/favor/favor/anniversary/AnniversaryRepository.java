package com.favor.favor.anniversary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnniversaryRepository extends JpaRepository<Anniversary, Long> {

    Optional<Anniversary> findAnniversaryByAnniversaryNo(Long anniversaryNo);
    Boolean existsByAnniversaryNo(Long anniversaryNo);
    void deleteByAnniversaryNo(Long anniversaryNo);
}
