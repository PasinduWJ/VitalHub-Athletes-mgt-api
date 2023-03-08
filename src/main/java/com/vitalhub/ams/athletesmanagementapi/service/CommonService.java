package com.vitalhub.ams.athletesmanagementapi.service;

import com.vitalhub.ams.athletesmanagementapi.dto.CountryDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.EventDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.GenderDTO;

import java.util.List;

public interface CommonService {
    public List<CountryDTO> getAllCountry();

    public List<GenderDTO> getAllGender();

    public List<EventDTO> getAllEvent();
}
