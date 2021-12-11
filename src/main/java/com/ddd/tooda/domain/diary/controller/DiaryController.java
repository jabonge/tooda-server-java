package com.ddd.tooda.domain.diary.controller;

import com.ddd.tooda.common.LoginUserId;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

//    @PostMapping()
//    ResponseEntity<DiaryDto.DiaryResponse> create(@LoginUserId Long userId, @Valid @RequestBody DiaryDto.CreateDiaryRequest req) {
//        DiaryDto.DiaryResponse response = diaryService
//        return ResponseEntity.ok();
//    }


}
