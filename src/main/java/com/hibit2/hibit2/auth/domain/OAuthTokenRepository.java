package com.hibit2.hibit2.auth.domain;

import com.hibit2.hibit2.auth.domain.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.hibit2.hibit2.auth.exception.NoSuchOAuthTokenException;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Long> {
    boolean existsByMemberId(final Long memberId);

    @Query("SELECT o "
            + "FROM OAuthToken o "
            + "WHERE o.member.id = :memberId")
    Optional<OAuthToken> findByMemberId(@Param("memberId")  Long memberId);

    default OAuthToken getByMemberId(final Long memberId) {
        return findByMemberId(memberId)
                .orElseThrow(NoSuchOAuthTokenException::new);
    }
}
