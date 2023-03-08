package com.vitalhub.ams.athletesmanagementapi.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchAthleteRequestDTO {
    private String name;
    private String countryId;
    private String genderId;
    private String eventId;
}
