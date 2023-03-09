package com.vitalhub.ams.athletesmanagementapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gender")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gender {

    @Id
    @Column(name = "gender_id")
    private String genderId;

    @Column(name = "gender", length = 20)
    private String gender;

    @OneToMany(mappedBy = "gender")
    private Set<Athlete> athletes;

    public Gender(String genderId) {
        this.genderId = genderId;
    }
}
