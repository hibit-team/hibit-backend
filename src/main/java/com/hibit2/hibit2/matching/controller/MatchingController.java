package com.hibit2.hibit2.matching.controller;


import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.matching.service.MatchingService;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.dto.UserlistDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/matching")
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    //매칭 신청자 리스트 확인
    @GetMapping("/{post_idx}/list")
    @Operation(summary = "/matching/1/list", description = "매칭 신청자 리스트 확인")
    public ResponseEntity<List<UserlistDto>> getMatchRequestsByPost(@PathVariable int post_idx) {
        List<Matching> matchRequests = matchingService.getMatchRequestsByPost(post_idx);
        List<UserlistDto> userlistDtoList = new ArrayList<>();
        for (Matching matching : matchRequests) {
            Users user = matching.getUser();
            UserlistDto userlistDto = new UserlistDto();
            userlistDto.setIdx(user.getIdx());
            userlistDto.setId(user.getId());
            userlistDto.setProfileImg(user.getProfileImg());

            userlistDtoList.add(userlistDto);
        }

        return ResponseEntity.ok(userlistDtoList);
    }
    //초대장 발송
    @PutMapping("/{post_idx}/send")
    @Operation(summary = "/matching/1/send", description = "초대장 발송")
    public ResponseEntity<Void> sendInvitations(@PathVariable int post_idx, @RequestBody List<String> userIds) {
        matchingService.sendInvitations(post_idx, userIds);
        return ResponseEntity.ok().build();
    }
    //매칭 수락 (추후 알림에서 변경 가능)
    @PutMapping("/{matching_idx}/ok")
    @Operation(summary = "/matching/1/ok", description = "매칭 수락 변경")
    public ResponseEntity<Void> okMatch(@PathVariable int matching_idx) {
        matchingService.okMatch(matching_idx);
        return ResponseEntity.ok().build();
    }
    //매칭 수락 (추후 알림에서 변경 가능)
    @PutMapping("/{matching_idx}/no")
    @Operation(summary = "/matching/1/no", description = "매칭 거절 변경")
    public ResponseEntity<Void> noMatch(@PathVariable int matching_idx) {
        matchingService.noMatch(matching_idx);
        return ResponseEntity.ok().build();
    }

    //매칭에서 ok한 사람들 list반환
    @GetMapping("/{post_idx}/oklist")
    @Operation(summary = "/matching/1/oklist", description = "매칭 수락자 리스트")
    public ResponseEntity<List<String>> getMatchUserByPost(@PathVariable int post_idx) {
        List<String> matchUsers = matchingService.getMatchUserByPost(post_idx);
        return ResponseEntity.ok(matchUsers);
    }


    //매칭 진짜 다녀온 사람들 리스트 history에 추가






}
