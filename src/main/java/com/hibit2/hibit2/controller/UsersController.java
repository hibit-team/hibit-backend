package com.hibit2.hibit2.controller;

import com.hibit2.hibit2.domain.Comment;
import com.hibit2.hibit2.domain.Users;
import com.hibit2.hibit2.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users", description = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    @PostMapping("/signup")
    @Operation(summary = "users/signup", description = "회원가입")
    public String save(@RequestBody String id){
        Users user = usersService.saveUsers(id);
        return user.getId();
    }
}
