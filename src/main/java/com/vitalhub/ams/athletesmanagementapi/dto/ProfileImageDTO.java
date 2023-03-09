package com.vitalhub.ams.athletesmanagementapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileImageDTO {
    private String imageName;
    private String imageType;
    private String imageData;
}
