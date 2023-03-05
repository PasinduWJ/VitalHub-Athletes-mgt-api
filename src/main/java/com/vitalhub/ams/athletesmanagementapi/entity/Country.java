package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "country")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @Column(name = "country_id")
    private String countryId;

    @Column(name = "country", length = 100)
    private String country;

    @OneToMany(mappedBy = "country")
    private Set<Athlete> athletes;
}
