package com.hibit2.hibit2.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hibit2.hibit2.profile.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
