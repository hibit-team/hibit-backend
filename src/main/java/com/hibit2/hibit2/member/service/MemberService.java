package com.hibit2.hibit2.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.dto.MemberResponse;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.member.exception.NotFoundMemberException;

@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member save(final Member member) {
        return memberRepository.save(member);
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(NotFoundMemberException::new);
    }

    public boolean existByEmail(final String email) {
        return memberRepository.existsByEmail(email);
    }
    public MemberResponse findById(final Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);

        return new MemberResponse(member);
    }
}
