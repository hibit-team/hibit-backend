package com.hibit2.hibit2.service;

import com.hibit2.hibit2.domain.Users;
import com.hibit2.hibit2.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional
    public Users saveUsers(String id){
        Users users = new Users();
        users.setId(id);
        return usersRepository.save(users);
    }

    @Transactional
    public Users findById(int idx){
        Users entity = usersRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다. id="+idx));
        return entity;
    }

}
