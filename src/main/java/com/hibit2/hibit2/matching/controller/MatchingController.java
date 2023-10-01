package com.hibit2.hibit2.matching.controller;


import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.matching.service.MatchingService;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.dto.MemberListDto;
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
    public ResponseEntity<List<MemberListDto>> getMatchRequestsByPost(@PathVariable int post_idx) {
        List<Matching> matchRequests = matchingService.getMatchRequestsByPost(post_idx);
        List<MemberListDto> memberListDtoList =new ArrayList<>();
        for (Matching matching : matchRequests) {


            Member member=matching.getMember();
            MemberListDto memberListDto = new MemberListDto();
            memberListDto.setIdx(member.getId());
            memberListDto.setId(member.getNickname());
            memberListDto.setMainimg(member.getMainImg());

            memberListDtoList.add(memberListDto);
        }

        return ResponseEntity.ok(memberListDtoList);
    }
    //초대장 발송
    @PutMapping("/{post_idx}/send")
    @Operation(summary = "/matching/1/send", description = "초대장 발송")
    public ResponseEntity<Void> sendInvitations(@PathVariable int post_idx, @RequestBody List<Long> memberIds) {
        matchingService.sendInvitations(post_idx, memberIds);
        return ResponseEntity.ok().build();
    }
    //매칭 수락 (추후 알림에서 변경 가능)
    @PutMapping("/{matching_idx}/ok")
    @Operation(summary = "/matching/1/ok", description = "매칭 수락 변경")
    public ResponseEntity<Void> okMatch(@PathVariable int matching_idx) {
        matchingService.okMatch(matching_idx);

        return ResponseEntity.ok().build();
    }
    //매칭 거절 (추후 알림에서 변경 가능)
    @PutMapping("/{matching_idx}/no")
    @Operation(summary = "/matching/1/no", description = "매칭 거절 변경")
    public ResponseEntity<Void> noMatch(@PathVariable int matching_idx) {
        matchingService.noMatch(matching_idx);
        return ResponseEntity.ok().build();
    }

    //매칭에서 ok한 사람들 list반환
    @GetMapping("/{post_idx}/oklist")
    @Operation(summary = "/matching/1/oklist", description = "매칭 수락자 리스트")
    public ResponseEntity<List<MemberListDto>> getMatchUserByPost(@PathVariable int post_idx) {
        List<Member> matchUsers = matchingService.getMatchUserByPost(post_idx);
        //matchUsers에 member에서 닉네임 리스트로 받아오기
        List<MemberListDto> matchUserList = new ArrayList<>();

        for (Member member : matchUsers) {
            matchUserList.add(new MemberListDto(member));
        }
        return ResponseEntity.ok(matchUserList);
    }


    //매칭 수락하고 진짜 간 사람들 받아와서 history 저장
    @PutMapping("/{post_idx}/oksave")
    @Operation(summary = "/matching/1/oksave", description = "진짜 간 유저 저장")
    public ResponseEntity<Void> saveOk(@PathVariable int post_idx, @RequestBody List<Long> memberIds) {
        matchingService.saveOkuser(post_idx, memberIds);
        return ResponseEntity.ok().build();
    }


}


