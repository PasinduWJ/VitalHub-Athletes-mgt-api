package com.vitalhub.ams.athletesmanagementapi.service.impl;

import com.vitalhub.ams.athletesmanagementapi.dto.CountryDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.EventDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.GenderDTO;
import com.vitalhub.ams.athletesmanagementapi.entity.Country;
import com.vitalhub.ams.athletesmanagementapi.entity.Event;
import com.vitalhub.ams.athletesmanagementapi.entity.Gender;
import com.vitalhub.ams.athletesmanagementapi.repository.CountryRepository;
import com.vitalhub.ams.athletesmanagementapi.repository.EventRepository;
import com.vitalhub.ams.athletesmanagementapi.repository.GenderRepository;
import com.vitalhub.ams.athletesmanagementapi.service.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

    private final ModelMapper modelMapper;
    private final CountryRepository countryRepository;
    private final GenderRepository genderRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CommonServiceImpl(ModelMapper modelMapper, CountryRepository countryRepository, GenderRepository genderRepository, EventRepository eventRepository) {
        this.modelMapper = modelMapper;
        this.countryRepository = countryRepository;
        this.genderRepository = genderRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CountryDTO> getAllCountry() {
        List<Country> countryList = countryRepository.findAll();
        return countryList.stream().map(country -> new CountryDTO(country.getCountryId(),country.getCountry())).collect(Collectors.toList());
    }

    @Override
    public List<GenderDTO> getAllGender() {
        List<Gender> genderList = genderRepository.findAll();
        return genderList.stream().map(gender -> new GenderDTO(gender.getGenderId(),gender.getGenderId())).collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getAllEvent() {
        List<Event> eventList = eventRepository.findAll();
        return eventList.stream().map(event -> new EventDTO(event.getEventId(), event.getEventName())).collect(Collectors.toList());
    }
}
