package com.hibit2.hibit2.declaration.controller;

import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.repository.AlarmRepository;
import com.hibit2.hibit2.declaration.domain.Declaration;
import com.hibit2.hibit2.declaration.dto.DeclarationSaveDto;
import com.hibit2.hibit2.declaration.repository.DeclarationRepository;
import com.hibit2.hibit2.declaration.service.DeclarationService;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "declaration", description = "신고")
@RestController
@RequiredArgsConstructor
@RequestMapping("/declaration")
public class DeclarationController {
    private final DeclarationService declarationService;
    private final DeclarationRepository declarationRepository;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/report")
    @Operation(summary = "declaration/report", description = "게시글 또는 댓글을 신고합니다.")
    public ResponseEntity<String> report(@RequestBody DeclarationSaveDto declarationSaveDto) {
        declarationService.createDeclaration(declarationSaveDto);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/list")
    @Operation(summary = "declaration/list", description = "모든 신고 내역을 조회합니다.")
    public ResponseEntity<List<Declaration>> getDeclarationList() {
        List<Declaration> declarationList = declarationRepository.findAll();
        return ResponseEntity.ok(declarationList);
    }

    //신고 접수
    @PutMapping("/count/{idx}")
    @Operation(summary = "declaration/count/1", description = "신고 내역 검토 후 횟수 증가")
    public ResponseEntity<Declaration> countReport(@PathVariable Integer idx){
        Declaration declaration = declarationRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 신고가 없습니다. id="+idx));
        declaration.changeRead();
        //추후 관리자 아이디로 변경
        Member sender = memberRepository.findByNickname("관리자")
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        //회원 테이블 신고 횟수 증가
        declaration.getMember().AddReport();

        //신고 접수 알람
        Alarm alarm = new Alarm();
        alarm.setReceiver(declaration.getMember());
        alarm.setSender(sender);
        alarm.setAlarmType(AlarmType.REPORT);
        alarm.setUrl("");
        alarm.setContent("회원님의 계정이 서비스 이용 수칙을 위반하여 신고되었습니다.");
        alarmRepository.save(alarm);

        return ResponseEntity.status(HttpStatus.CREATED).body(declaration);
    }

    //신고 내역 읽음 처리
    @PutMapping("/read/{idx}")
    @Operation(summary = "declaration/read/1", description = "신고 내역 읽음 처리")
    public ResponseEntity<Declaration> changeRead(@PathVariable Integer idx){
        Declaration declaration = declarationRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 신고가 없습니다. id="+idx));
        declaration.changeRead();
        return ResponseEntity.status(HttpStatus.CREATED).body(declaration);
    }

}
