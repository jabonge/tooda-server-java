package com.ddd.tooda.domain.diary.controller;

import com.ddd.tooda.common.LoginUserId;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DiaryDto.DiaryResponse> create(@LoginUserId Long userId, @Valid @RequestBody DiaryDto.CreateDiaryRequest req) {
        DiaryDto.DiaryResponse response = diaryService.createDiary(userId,req);
        return ResponseEntity.ok(response);
    }

}
