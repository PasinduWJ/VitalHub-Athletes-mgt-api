package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event {

    @Id
    @Column(name = "event_id")
    private String eventId;

    @Column(name = "event_name", length = 100)
    private String eventName;

    @OneToMany(mappedBy = "event")
    Set<AthleteEvent> athleteEvents;

//    @ManyToMany(mappedBy = "events")
//    Set<Athlete> athletes;


    public Event(String eventId) {
        this.eventId = eventId;
    }
}
