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

import static com.ddd.tooda.domain.diary.dto.DiaryDto.*;

@RestController
@RequestMapping(DiaryController.DIARY)
@RequiredArgsConstructor
public class DiaryController {

    public static final String DIARY = "/diary";

    private final DiaryService diaryService;
    private final S3FileUploader s3FileUploader;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DiaryDetailResponse> create(@LoginUserId Long userId,
                                         @Valid @RequestBody CreateDiaryRequest req) {
        DiaryDetailResponse response = diaryService.createDiary(userId, req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    ResponseEntity<DiaryDetailResponse> findOne(@LoginUserId Long userId,
                                          @PathVariable("id") Long diaryId) {
        DiaryDetailResponse response = diaryService.findOne(userId, diaryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    ResponseEntity<DiaryDetailResponse> update(@LoginUserId Long userId,
                                          @PathVariable("id") Long diaryId, @Valid @RequestBody CreateDiaryRequest req) {
        DiaryDetailResponse response = diaryService.updateDiary(userId, diaryId, req);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    ResponseEntity<NoOffsetDiaryResponses> findAll(@LoginUserId Long userId,
                                                @Valid NoOffsetPaginationDto noOffsetPaginationDto) {
        NoOffsetDiaryResponses responses = diaryService.findAll(userId, noOffsetPaginationDto);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("date")
    ResponseEntity<NoOffsetDiaryResponses> findAllByDate(@LoginUserId Long userId,
                                                      @Valid FindAllByDateRequest req,
    @Valid NoOffsetPaginationDto noOffsetPaginationDto) {
        NoOffsetDiaryResponses responses = diaryService.findAllByDate(userId, req,noOffsetPaginationDto);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("search")
    ResponseEntity<NoOffsetDiaryResponses> search(@LoginUserId Long userId,@RequestParam String q,
                                               @Valid NoOffsetPaginationDto noOffsetPaginationDto) {
        NoOffsetDiaryResponses responses = diaryService.search(userId, q, noOffsetPaginationDto);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> remove(@LoginUserId Long userId, @PathVariable("id") Long diaryId) {
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
