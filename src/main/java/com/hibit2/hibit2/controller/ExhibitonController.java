package com.hibit2.hibit2.controller;


import com.hibit2.hibit2.domain.Exhibition;
import com.hibit2.hibit2.repository.ExhibitionRepository;
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

@Tag(name = "exhibiton", description = "전시회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/exhibiton")
public class ExhibitonController {
    private final ExhibitionRepository exhibitionRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Exhibition>> getExhibitionList() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Exhibition> exhibitionList = exhibitionRepository.findByFinishDateAfter(currentDate);
        return ResponseEntity.ok(exhibitionList);
    }
}
