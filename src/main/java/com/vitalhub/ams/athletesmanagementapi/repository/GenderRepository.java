package com.vitalhub.ams.athletesmanagementapi.repository;

import com.vitalhub.ams.athletesmanagementapi.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, String> {
}
