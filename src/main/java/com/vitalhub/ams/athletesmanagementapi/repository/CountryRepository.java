package com.vitalhub.ams.athletesmanagementapi.repository;

import com.vitalhub.ams.athletesmanagementapi.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
