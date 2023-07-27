package com.hibit2.hibit2.postHistory.controller;


import com.hibit2.hibit2.postHistory.domain.postHistory;
import com.hibit2.hibit2.postHistory.service.postHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class postHistoryController {
    private postHistoryService postHistoryService;

    @Autowired
    public postHistoryController(postHistoryService postHistoryService) {
        this.postHistoryService = postHistoryService;
    }


    @GetMapping("/{post_idx}")
    @Operation(summary = "/history/1", description = "매칭 히스토리")
    public ResponseEntity<postHistory> findByPostIdx(@PathVariable int post_idx){
        postHistory postHistory= postHistoryService.findById(post_idx);
        return ResponseEntity.status(HttpStatus.CREATED).body(postHistory);
    }

}
