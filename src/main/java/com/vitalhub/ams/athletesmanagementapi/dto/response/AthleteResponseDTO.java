package com.vitalhub.ams.athletesmanagementapi.dto.response;

import com.vitalhub.ams.athletesmanagementapi.dto.AthleteEventDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AthleteResponseDTO {
    private String firstName;
    private String lastName;
    private String countryId;
    private String genderId;
    private Date dob;
    private String photo;
    private List<AthleteEventDTO> athleteEvents;
}
