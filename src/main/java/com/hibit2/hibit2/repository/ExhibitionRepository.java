package com.hibit2.hibit2.repository;

import com.hibit2.hibit2.domain.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {
    List<Exhibition> findByFinishDateAfter(LocalDateTime date);

}
