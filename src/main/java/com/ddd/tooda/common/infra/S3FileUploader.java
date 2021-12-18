package com.ddd.tooda.common.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class S3FileUploader {

    private final AmazonS3Client s3Client;
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${spring.profiles.active}")
    private String profile;

    public String upload(MultipartFile file, String dirName) {
        return putS3(file, dirName);
    }

    public List<String> uploadAll(List<MultipartFile> files, String dirName) {
        return files.stream().map(v -> upload(v,dirName)).collect(Collectors.toList());
    }

    private String putS3(MultipartFile multipartFile, String dirName) {
        String fileName = profile + "/" + dirName + "/" + UUID.randomUUID();
        try {
            s3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), null).withCannedAcl(
                    CannedAccessControlList.PublicRead
            ));
        } catch (IOException e) {
            throw new RuntimeException("파일 변환 오류: " + e.getLocalizedMessage());
        }
        return s3Client.getUrl(bucket, fileName).toString();
    }

}
