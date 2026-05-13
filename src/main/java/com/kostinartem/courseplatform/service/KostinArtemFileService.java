package com.kostinartem.courseplatform.service;

import com.kostinartem.courseplatform.dto.KostinArtemFileResponseDto;
import com.kostinartem.courseplatform.entity.KostinArtemCourse;
import com.kostinartem.courseplatform.entity.KostinArtemFileResource;
import com.kostinartem.courseplatform.exception.KostinArtemBadRequestException;
import com.kostinartem.courseplatform.exception.KostinArtemNotFoundException;
import com.kostinartem.courseplatform.repository.KostinArtemCourseRepository;
import com.kostinartem.courseplatform.repository.KostinArtemFileResourceRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class KostinArtemFileService {

    private final KostinArtemFileResourceRepository fileResourceRepository;
    private final KostinArtemCourseRepository courseRepository;

    public KostinArtemFileResponseDto uploadFile(MultipartFile file, Long courseId) {
        if (file == null || file.isEmpty()) {
            throw new KostinArtemBadRequestException("File is empty");
        }

        KostinArtemCourse course = courseRepository.findById(courseId)
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + courseId));

        try {
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);

            String originalName = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalName.replace(" ", "_");
            Path filePath = uploadDir.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            KostinArtemFileResource fileResource = new KostinArtemFileResource();
            fileResource.setFileName(fileName);
            fileResource.setOriginalName(originalName);
            fileResource.setContentType(file.getContentType());
            fileResource.setFilePath(filePath.toString());
            fileResource.setSize(file.getSize());
            fileResource.setCourse(course);

            return mapToResponse(fileResourceRepository.save(fileResource));
        } catch (IOException exception) {
            throw new KostinArtemBadRequestException("Failed to upload file");
        }
    }

    public Resource downloadFile(Long fileId) {
        KostinArtemFileResource fileResource = fileResourceRepository.findById(fileId)
                .orElseThrow(() -> new KostinArtemNotFoundException("File not found with id: " + fileId));

        try {
            Path filePath = Paths.get(fileResource.getFilePath());
            if (!Files.exists(filePath)) {
                throw new KostinArtemNotFoundException("File not found");
            }

            return new UrlResource(filePath.toUri());
        } catch (IOException exception) {
            throw new KostinArtemNotFoundException("File not found");
        }
    }

    public KostinArtemFileResource getFileMetadata(Long fileId) {
        return fileResourceRepository.findById(fileId)
                .orElseThrow(() -> new KostinArtemNotFoundException("File not found with id: " + fileId));
    }

    private KostinArtemFileResponseDto mapToResponse(KostinArtemFileResource fileResource) {
        KostinArtemFileResponseDto dto = new KostinArtemFileResponseDto();
        dto.setId(fileResource.getId());
        dto.setFileName(fileResource.getFileName());
        dto.setOriginalName(fileResource.getOriginalName());
        dto.setContentType(fileResource.getContentType());
        dto.setFilePath(fileResource.getFilePath());
        dto.setSize(fileResource.getSize());
        dto.setUploadedAt(fileResource.getUploadedAt());
        dto.setCourseId(fileResource.getCourse().getId());
        return dto;
    }
}
