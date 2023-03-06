package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage {

    @Column(name = "image_name", length = 100)
    private String imageName;

    @Column(name = "image_type", length = 100)
    private String imageType;

    @Column(name = "image_data", length = 1000)
    private byte[] imageData;

}
