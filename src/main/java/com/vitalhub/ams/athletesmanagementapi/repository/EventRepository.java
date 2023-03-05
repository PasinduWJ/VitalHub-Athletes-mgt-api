package com.vitalhub.ams.athletesmanagementapi.repository;

import com.vitalhub.ams.athletesmanagementapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
