package com.kostinartem.courseplatform.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KostinArtemFileResponseDto {

    private Long id;
    private String fileName;
    private String originalName;
    private String contentType;
    private String filePath;
    private Long size;
    private LocalDateTime uploadedAt;
    private Long courseId;
}
