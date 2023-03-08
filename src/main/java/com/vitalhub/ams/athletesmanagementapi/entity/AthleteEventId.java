package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
