package com.vitalhub.ams.athletesmanagementapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AthleteEventDTO {
    private EventDTO event;
    private String result;
}
