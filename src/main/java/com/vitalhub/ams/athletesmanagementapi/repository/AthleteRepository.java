package com.vitalhub.ams.athletesmanagementapi.repository;

import com.vitalhub.ams.athletesmanagementapi.entity.Athlete;
import com.vitalhub.ams.athletesmanagementapi.entity.Country;
import com.vitalhub.ams.athletesmanagementapi.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, String> {
    public Optional<Athlete> findAthleteByFirstNameAndLastNameAndDobAndGenderAndCountry(
            String fistName, String lastName, Date dob, Gender gender, Country country);
}
