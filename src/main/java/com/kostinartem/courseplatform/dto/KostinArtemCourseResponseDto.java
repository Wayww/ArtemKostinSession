package com.kostinartem.courseplatform.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KostinArtemCourseResponseDto {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String level;
    private BigDecimal price;
    private Boolean active;
    private LocalDateTime createdAt;
    private Long teacherId;
    private String teacherName;
}
