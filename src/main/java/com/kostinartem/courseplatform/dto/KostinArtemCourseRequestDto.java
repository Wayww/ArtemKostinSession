package com.kostinartem.courseplatform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KostinArtemCourseRequestDto {

    @NotBlank
    @Size(min = 2, max = 255)
    private String title;

    @NotBlank
    @Size(min = 5, max = 2000)
    private String description;

    @NotBlank
    private String category;

    @NotBlank
    private String level;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    private Boolean active;

    @NotNull
    private Long teacherId;
}
