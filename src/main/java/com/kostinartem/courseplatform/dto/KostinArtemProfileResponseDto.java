package com.kostinartem.courseplatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KostinArtemProfileResponseDto {

    private Long id;
    private String bio;
    private String phoneNumber;
    private String avatarUrl;
    private Long userId;
}
