package com.kostinartem.courseplatform.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KostinArtemLessonResponseDto {

    private Long id;
    private String title;
    private String content;
    private String videoUrl;
    private Integer lessonOrder;
    private LocalDateTime createdAt;
    private Long courseId;
}
