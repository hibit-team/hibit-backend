package com.hibit2.hibit2.user.repository;

import com.hibit2.hibit2.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findById(String id);
}
