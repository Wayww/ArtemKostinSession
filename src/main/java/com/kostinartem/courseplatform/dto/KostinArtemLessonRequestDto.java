package com.kostinartem.courseplatform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KostinArtemLessonRequestDto {

    @NotBlank
    @Size(min = 2, max = 255)
    private String title;

    @NotBlank
    @Size(min = 5, max = 5000)
    private String content;

    private String videoUrl;

    @NotNull
    @Min(1)
    private Integer lessonOrder;

    @NotNull
    private Long courseId;
}
