package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemFileResponseDto;
import com.kostinartem.courseplatform.entity.KostinArtemFileResource;
import com.kostinartem.courseplatform.service.KostinArtemFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class KostinArtemFileController {

    private final KostinArtemFileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<KostinArtemFileResponseDto> uploadFile(@RequestPart MultipartFile file,
                                                                 @RequestParam Long courseId) {
        return ResponseEntity.ok(fileService.uploadFile(file, courseId));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        Resource resource = fileService.downloadFile(fileId);
        KostinArtemFileResource metadata = fileService.getFileMetadata(fileId);

        String contentType = metadata.getContentType() == null
                ? MediaType.APPLICATION_OCTET_STREAM_VALUE
                : metadata.getContentType();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + metadata.getOriginalName() + "\"")
                .body(resource);
    }
}
