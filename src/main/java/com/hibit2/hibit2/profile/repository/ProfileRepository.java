package com.hibit2.hibit2.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hibit2.hibit2.profile.domain.Profile;
import com.hibit2.hibit2.profile.exception.NotFoundProfileException;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    default Profile getById(final Long id) {
        return this.findById(id)
                .orElseThrow(NotFoundProfileException::new);
    }

    @Query("SELECT p "
            + "FROM Profile p "
            + "WHERE p.member.id = :memberId")
    Optional<Profile> findByMemberId(@Param("memberId") Long memberId);

    boolean existsByNickname(String nickname);
}