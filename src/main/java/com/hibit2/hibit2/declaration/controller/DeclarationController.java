package com.hibit2.hibit2.declaration.controller;

import com.hibit2.hibit2.declaration.domain.Declaration;
import com.hibit2.hibit2.declaration.dto.DeclarationSaveDto;
import com.hibit2.hibit2.declaration.repository.DeclarationRepository;
import com.hibit2.hibit2.declaration.service.DeclarationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "declaration", description = "신고")
@RestController
@RequiredArgsConstructor
@RequestMapping("/declaration")
public class DeclarationController {
    private final DeclarationService declarationService;
    private final DeclarationRepository declarationRepository;

    @PostMapping("/report")
    @Operation(summary = "declaration/report", description = "게시글 또는 댓글을 신고합니다.")
    public ResponseEntity<Declaration> report(@RequestBody DeclarationSaveDto declarationSaveDto) {
        Declaration declaration = declarationService.createDeclaration(declarationSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(declaration);
    }

    //관리자가 신고 내역 조회하고, 확인하는 과정
    @PutMapping("/count/{idx}")
    @Operation(summary = "declaration/count/1", description = "신고 내역 검토 후 횟수 증가")
    public ResponseEntity<Declaration> countReport(@PathVariable Integer idx){
        Declaration declaration = declarationRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 신고가 없습니다. id="+idx));
        declaration.changeRead();
        //회원 테이블 신고 횟수 증가
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
