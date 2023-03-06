package com.vitalhub.ams.athletesmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImageDTO {
    private String imageName;
    private String imageType;
    private String imageData;
}
