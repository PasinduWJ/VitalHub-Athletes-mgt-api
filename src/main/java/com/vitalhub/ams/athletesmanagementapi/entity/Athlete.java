package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "athlete")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Athlete {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "athlete_id")
    private String athleteId;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @JoinColumn(name="country", nullable=false)
    private String country;

    @Column(name = "dob", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "photo")
    private String photo;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "last_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "athlete_event",
//            joinColumns = @JoinColumn(name = "athlete_id"),
//            inverseJoinColumns = @JoinColumn(name = "event_id"))
//    private Set<Event> events;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
    private Set<AthleteEvent> athleteEvents;


}
