package com.hibit2.hibit2.member.controller;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.auth.presentation.AuthenticationPrincipal;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.dto.MemberIdxDto;
import com.hibit2.hibit2.member.exception.NotFoundMemberException;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.member.service.MemberService;
import com.hibit2.hibit2.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "members", description = "회원")
@RequestMapping("/api/members")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberController(final MemberService memberService, final MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> findMe(@AuthenticationPrincipal final LoginMember loginMember) {
        MemberResponse response = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/find")
    public ResponseEntity<MemberIdxDto> findIdx(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember){
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(NotFoundMemberException::new);

        MemberIdxDto memberIdxDto = new MemberIdxDto(member);
        return ResponseEntity.ok(memberIdxDto);
    }

}
