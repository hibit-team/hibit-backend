package com.hibit2.hibit2.mail.service;


import com.hibit2.hibit2.user.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Slf4j
@Service
@RequiredArgsConstructor

public class EmailService {
    private final JavaMailSender javaMailSender;

    public ResponseEntity<String> mailSend(Users user, String title, String content){

        if (user.getEmail()== null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 주소를 지정해야 합니다.");
        }

        if (!isValidEmailAddress(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 이메일 주소입니다.");
        }


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(title);
        message.setText(content);
        javaMailSender.send(message);

        return ResponseEntity.ok("이메일이 성공적으로 발송되었습니다.");

    }

    // 이메일 주소의 유효성 검사 메서드
    private boolean isValidEmailAddress(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

}
