package com.hibit2.hibit2.postHistory.repository;

import com.hibit2.hibit2.postHistory.domain.postHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface postHistoryRepository extends JpaRepository<postHistory, Integer> {
    postHistory findByPostIdx(int post_idx);

}
