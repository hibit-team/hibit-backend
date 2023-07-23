package com.hibit2.hibit2.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hibit2.hibit2.member.exception.NoSuchMemberException;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);

    default Member getById(final Long id) {
        return findById(id)
            .orElseThrow(NoSuchMemberException::new);
    }
    boolean existsByEmail(final String email);
}