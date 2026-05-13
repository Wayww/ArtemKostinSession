package com.kostinartem.courseplatform.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KostinArtemEnrollmentResponseDto {

    private Long id;
    private String status;
    private LocalDateTime enrolledAt;
    private Long userId;
    private Long courseId;
}
