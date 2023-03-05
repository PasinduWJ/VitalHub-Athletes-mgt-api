package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "athlete_event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AthleteEvent {

    @EmbeddedId
    private AthleteEventId athleteEventId = new AthleteEventId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "athlete_id")
    @MapsId("athleteId")
    private Athlete athlete;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    @MapsId("eventId")
    private Event event;

    @Column(name = "result")
    private String result;

    public AthleteEvent(Athlete athlete, Event event) {
        this.athlete = athlete;
        this.event = event;
    }
}
