package com.ddd.tooda.domain.diary.controller;

import com.ddd.tooda.common.LoginUserId;
import com.ddd.tooda.common.NoOffsetPaginationDto;
import com.ddd.tooda.common.infra.S3FileUploader;
import com.ddd.tooda.domain.diary.dto.DiaryDto;
import com.ddd.tooda.domain.diary.dto.DiaryDto.DiaryResponse;
import com.ddd.tooda.domain.diary.dto.MonthlyDiaryMetaDto.MonthlyDiaryMetaResponse;
import com.ddd.tooda.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(DiaryController.DIARY)
@RequiredArgsConstructor
public class DiaryController {

    public static final String DIARY = "/diary";

    private final DiaryService diaryService;
    private final S3FileUploader s3FileUploader;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DiaryResponse> create(@LoginUserId Long userId,
                                         @Valid @RequestBody DiaryDto.CreateDiaryRequest req) {
        DiaryResponse response = diaryService.createDiary(userId, req);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    ResponseEntity<List<DiaryResponse>> findAll(@LoginUserId Long userId,
                                                @Valid NoOffsetPaginationDto noOffsetPaginationDto) {
        List<DiaryResponse> responses = diaryService.findAll(userId, noOffsetPaginationDto);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("date")
    ResponseEntity<List<DiaryResponse>> findAllByDate(@LoginUserId Long userId,
                                                      @Valid DiaryDto.FindAllByDateRequest req,
    @Valid NoOffsetPaginationDto noOffsetPaginationDto) {
        List<DiaryResponse> responses = diaryService.findAllByDate(userId, req,noOffsetPaginationDto);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("search")
    ResponseEntity<List<DiaryResponse>> search(@LoginUserId Long userId,@RequestParam String q,
                                               @Valid NoOffsetPaginationDto noOffsetPaginationDto) {
        List<DiaryResponse> responses = diaryService.search(userId, q, noOffsetPaginationDto);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("{id}")
    ResponseEntity remove(@LoginUserId Long userId, @PathVariable("id") Long diaryId) {
        diaryService.remove(userId, diaryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{year}/metas")
    ResponseEntity<List<MonthlyDiaryMetaResponse>> findMonthlyDiaryMetasByYear(@LoginUserId Long userId,
                                                                               @PathVariable int year) {
        List<MonthlyDiaryMetaResponse> responses = diaryService.findMonthlyDiaryMetasByYear(userId, year);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("image")
    ResponseEntity<List<String>> convertImagesToUrl(@RequestParam("files") List<MultipartFile> files)  {
        List<String> urls = s3FileUploader.uploadAll(files, "diary");
        return ResponseEntity.ok(urls);
    }


}
