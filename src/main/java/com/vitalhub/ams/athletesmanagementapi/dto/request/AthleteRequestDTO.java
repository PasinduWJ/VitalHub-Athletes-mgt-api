package com.vitalhub.ams.athletesmanagementapi.dto.request;
import com.vitalhub.ams.athletesmanagementapi.dto.AthleteEventDTO;
import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AthleteRequestDTO {
    private String firstName;
    private String lastName;
    private String country;
    private String genderId;
    private Date dob;
    private String photo;
    private List<String> eventIdList;
}
