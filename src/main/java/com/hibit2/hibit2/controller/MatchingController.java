package com.hibit2.hibit2.controller;

import com.hibit2.hibit2.domain.Matching;
import com.hibit2.hibit2.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matching")
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    //매칭 신청자 리스트 확인
    @GetMapping("/{post_idx}/list")
    public ResponseEntity<List<Matching>> getMatchRequestsByPost(@PathVariable int post_idx) {
        List<Matching> matchRequests = matchingService.getMatchRequestsByPost(post_idx);
        return ResponseEntity.ok(matchRequests);
    }
    //초대장 발송
    @PutMapping("/{post_idx}/send")
    public ResponseEntity<Void> sendInvitations(@PathVariable int post_idx, @RequestBody List<String> userIds) {
        matchingService.sendInvitations(post_idx, userIds);
        return ResponseEntity.ok().build();
    }
    //매칭 완료 변경
    @PutMapping("/{matching_idx}/complete")
    public ResponseEntity<Void> completeMatch(@PathVariable int matching_idx) {
        matchingService.completeMatch(matching_idx);
        return ResponseEntity.ok().build();
    }

}
