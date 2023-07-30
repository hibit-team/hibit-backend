package com.hibit2.hibit2.postHistory.service;

import com.hibit2.hibit2.post.dto.PostResponseDto;
import com.hibit2.hibit2.postHistory.domain.postHistory;
import com.hibit2.hibit2.postHistory.repository.postHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Service
public class postHistoryService {
    private final postHistoryRepository postHistoryRepository;

    @Transactional
    public postHistory findById(int idx) {
        postHistory entity = postHistoryRepository.findByPostIdx(idx);
        if (entity == null) {
            throw new EntityNotFoundException("postHistory not found with idx: " + idx);
        }

        return entity;
    }
}
