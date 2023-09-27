package com.hibit2.hibit2.global.controller;


import com.hibit2.hibit2.global.domain.Exhibition;
import com.hibit2.hibit2.global.repository.ExhibitionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "exhibiton", description = "전시회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/exhibition")
public class ExhibitonController {
    private final ExhibitionRepository exhibitionRepository;

    @GetMapping("/list")
    public ResponseEntity<List<String>> getExhibitionList() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Exhibition> exhibitionList = exhibitionRepository.findByFinishDateAfter(currentDate);
        List<String> exhibitionTitleList = exhibitionList.stream()
                .map(Exhibition::getTitle)
                .collect(Collectors.toList());
        return ResponseEntity.ok(exhibitionTitleList);
    }
}
