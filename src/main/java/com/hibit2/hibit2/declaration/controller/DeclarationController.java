package com.hibit2.hibit2.declaration.controller;

import com.hibit2.hibit2.declaration.domain.Declaration;
import com.hibit2.hibit2.declaration.dto.DeclarationSaveDto;
import com.hibit2.hibit2.declaration.service.DeclarationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "declaration", description = "신고")
@RestController
@RequiredArgsConstructor
@RequestMapping("/declaration")
public class DeclarationController {
    private final DeclarationService declarationService;

    @PostMapping("/report")
    @Operation(summary = "declaration/report", description = "게시글 또는 댓글을 신고합니다.")
    public ResponseEntity<Declaration> report(@RequestBody DeclarationSaveDto declarationSaveDto) {
        Declaration declaration = declarationService.createDeclaration(declarationSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(declaration);
    }

    //관리자가 신고 내역 조회하고, 확인하는 과정
}
