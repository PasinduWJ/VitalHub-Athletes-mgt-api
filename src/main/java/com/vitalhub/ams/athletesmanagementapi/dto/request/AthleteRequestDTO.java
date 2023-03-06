package com.vitalhub.ams.athletesmanagementapi.dto.request;

import com.vitalhub.ams.athletesmanagementapi.dto.ProfileImageDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AthleteRequestDTO {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String countryId;
    @NotEmpty
    private String genderId;

    @NotNull(message = "Please provide a date.")
    private Date dob;

    private ProfileImageDTO profileImage;
    @NotEmpty
    private List<String> eventIdList;
}
