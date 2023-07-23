package com.hibit2.hibit2.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hibit2.hibit2.profile.domain.Profile;
    default Profile getById(final Long id) {
        return this.findById(id)
            .orElseThrow(NotFoundProfileException::new);
    }

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
