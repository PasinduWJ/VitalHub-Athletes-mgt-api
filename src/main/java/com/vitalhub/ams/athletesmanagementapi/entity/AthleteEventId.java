package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AthleteEventId implements Serializable {

    @Column(name = "athlete_id", nullable = false)
    private String athleteId;

    @Column(name = "event_id", nullable = false)
    private String eventId;

}
