package com.hibit2.hibit2.repository;

import com.hibit2.hibit2.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findALlByDeleteYn(char deleteYn, Sort sort);
    Page<Post> findByDeleteYn(char flag, Pageable pageable);
    @Query("SELECT p FROM Post p JOIN p.dateTimeSlots dts WHERE p.deleteYn = :flag AND dts.date >= :startDate AND dts.date < :endDate")
    Page<Post> findByDateTimeRange(@Param("flag") char flag, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);


}
