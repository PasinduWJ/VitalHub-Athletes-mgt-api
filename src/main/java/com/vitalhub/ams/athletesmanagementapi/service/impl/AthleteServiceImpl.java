package com.vitalhub.ams.athletesmanagementapi.service.impl;

import com.vitalhub.ams.athletesmanagementapi.dto.ProfileImageDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.request.AthleteRequestDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.request.SearchAthleteRequestDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.response.AthleteResponseDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.response.CommonResponseDTO;
import com.vitalhub.ams.athletesmanagementapi.entity.*;
import com.vitalhub.ams.athletesmanagementapi.repository.AthleteRepository;
import com.vitalhub.ams.athletesmanagementapi.repository.EventRepository;
import com.vitalhub.ams.athletesmanagementapi.service.AthleteService;
import com.vitalhub.ams.athletesmanagementapi.util.FileCompress;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Transactional
public class AthleteServiceImpl implements AthleteService {

    private final AthleteRepository athleteRepository;
    private final FileCompress fileCompress;
    private final ModelMapper modelMapper;

    private final EventRepository eventRepository;

    @Autowired
    public AthleteServiceImpl(AthleteRepository athleteRepository, FileCompress fileCompress, ModelMapper modelMapper, EventRepository eventRepository) {
        this.athleteRepository = athleteRepository;
        this.fileCompress = fileCompress;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
    }

    @Override
    public CommonResponseDTO addAthlete(AthleteRequestDTO dto) {

        //check athlete age grater than 16
        LocalDate dobLocal = dto.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (Period.between(dobLocal, LocalDate.now()).getYears() > 16 && !dto.getEventIdList().isEmpty()) {  //todo

            Athlete athlete = modelMapper.map(dto, Athlete.class);
            athlete.setAthleteId("000");

            //check this like credentials has athlete in the system  t
            Optional<Athlete> existingAthlete = athleteRepository.findAthleteByFirstNameAndLastNameAndDobAndGenderAndCountry(
                    athlete.getFirstName(), athlete.getLastName(), athlete.getDob(), athlete.getGender(), athlete.getCountry());

            // not allowed to create athlete , user have to enter differnt ditails
            if (existingAthlete.isPresent()) {
                return new CommonResponseDTO(HttpStatus.BAD_REQUEST.value(), "The Athlete already exist.", dto);
            }

            try {
                //image compress
                ProfileImage image = athlete.getProfileImage();
                if (dto.getProfileImage() != null && dto.getProfileImage().getImageData() != "") {
                    byte[] compressData = fileCompress.compressBytes(Base64.getDecoder().decode(dto.getProfileImage().getImageData()));

                    image.setImageData(compressData);
                    athlete.setProfileImage(image);
                } else {
                    athlete.setProfileImage(null);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
            // set last update date and create date
            athlete.setCreated(new Date());
            athlete.setLastUpdate(new Date());

            // event set
            athlete.setAthleteEvents(dto.getEventIdList().stream()
                    .map(val -> new AthleteEvent(athlete, new Event(val))).collect(Collectors.toSet()));

            //save
            athleteRepository.save(athlete);
            return new CommonResponseDTO(HttpStatus.CREATED.value(), "Athlete added Successfully.", dto);
        } else {
            return new CommonResponseDTO(HttpStatus.BAD_REQUEST.value(), "Age must be grater than 16 years.", dto);
        }

    }

    @Override
    public CommonResponseDTO searchAthlete(SearchAthleteRequestDTO dto) {
        // for search athlete
        List<Athlete> athleteList = athleteRepository.findAll(where((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // check fist name or last name match with enter values
            if (dto.getName() != null && !dto.getName().isEmpty()) {
                predicates.add(cb.or(cb.like(cb.lower(root.get("firstName")), "%" + dto.getName().toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("lastName")), "%" + dto.getName().toLowerCase() + "%")));
            }
            if (dto.getGenderId() != null && !dto.getGenderId().isEmpty()) {
                Join<Athlete, Gender> join = root.join("gender");
                predicates.add(cb.equal(join.get("genderId"), dto.getGenderId()));
            }
            if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
                Join<Athlete, Country> join = root.join("country");
                predicates.add(cb.equal(join.get("countryId"), dto.getCountryId()));
            }
            if (dto.getEventId() != null && !dto.getEventId().isEmpty()) {
                Join<Athlete, AthleteEvent> join = root.join("athleteEvents");
                Join<AthleteEvent, Event> joinEvent = join.join("event");
                predicates.add(cb.equal(joinEvent.get("eventId"), dto.getEventId()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }));

        //set all filtered details
        if (!athleteList.isEmpty()) {
            List<AthleteResponseDTO> responseDTOList = athleteList.stream()
                    .map(athlete -> {
                        AthleteResponseDTO responseDTO = modelMapper.map(athlete, AthleteResponseDTO.class);
                        try {
                            ProfileImageDTO image = new ProfileImageDTO(athlete.getProfileImage().getImageName(), athlete.getProfileImage().getImageType(), "");
                            // image not set if not uploaded
                            if (athlete.getProfileImage() != null && athlete.getProfileImage().getImageData().length > 0) {
                                byte[] compressData = fileCompress.decompressBytes(athlete.getProfileImage().getImageData());
                                image.setImageData(Base64.getEncoder().encodeToString(compressData));
                                responseDTO.setProfileImage(image);
                            } else {
                                responseDTO.setProfileImage(new ProfileImageDTO());
                            }
                        } catch (Exception e) {
                            responseDTO.setProfileImage(new ProfileImageDTO());
                        }
                        return responseDTO;
                    }).collect(Collectors.toList());

            return new CommonResponseDTO(HttpStatus.OK.value(), "All athletes Details", responseDTOList);
        } else {
            return new CommonResponseDTO(HttpStatus.NOT_FOUND.value(), "Athlete Cannot found", null);
        }
    }

    //this used for update athlete to view Athlete details
    @Override
    public CommonResponseDTO getAthlete(String athleteId) throws Exception {
        // check system have athlete details
        Optional<Athlete> athlete = Optional.ofNullable(this.athleteRepository.findById(athleteId).orElseThrow(() ->
                new Exception("Not Found Athlete")));
        if (athlete.isPresent()) {
            AthleteResponseDTO responseDTO = modelMapper.map(athlete.get(), AthleteResponseDTO.class);
            try {
                ProfileImageDTO image = new ProfileImageDTO(athlete.get().getProfileImage().getImageName(), athlete.get().getProfileImage().getImageType(), "");
                // image not set if not uploaded
                if (athlete.get().getProfileImage() != null && athlete.get().getProfileImage().getImageData().length > 0) {
                    byte[] compressData = fileCompress.decompressBytes(athlete.get().getProfileImage().getImageData());
                    image.setImageData(Base64.getEncoder().encodeToString(compressData));
                    responseDTO.setProfileImage(image);
                } else {
                    responseDTO.setProfileImage(new ProfileImageDTO());
                }
            } catch (Exception e) {
                responseDTO.setProfileImage(new ProfileImageDTO());
            }
            AthleteResponseDTO responseDTOList = responseDTO;
            return new CommonResponseDTO(HttpStatus.OK.value(), "Athletes Details", responseDTOList);
        } else {
            return new CommonResponseDTO(HttpStatus.NOT_FOUND.value(), "Athlete Cannot found", null);
        }
    }

    @Override
    public CommonResponseDTO updateAthlete(String id, AthleteRequestDTO dto) {

        //check athlete age is grater than 16
        LocalDate dobLocal = dto.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (Period.between(dobLocal, LocalDate.now()).getYears() > 16 && !dto.getEventIdList().isEmpty()) {  //todo
            // Load the athlete to update from database
            Optional<Athlete> existAthlete = athleteRepository.findById(id);
            if (existAthlete.isPresent()) {
                Athlete athlete = existAthlete.get();

                //--------------- update the athlete's details'
                athlete.setFirstName(dto.getFirstName());
                athlete.setLastName(dto.getLastName());
                athlete.setDob(dto.getDob());
                athlete.setCountry(new Country(dto.getCountryId()));
                athlete.setGender(new Gender(dto.getGenderId()));
                athlete.setLastUpdate(new Date());

                athleteRepository.save(athlete);
                return new CommonResponseDTO(HttpStatus.CREATED.value(), "Athlete Update Successfully.", dto);
            } else {
                return new CommonResponseDTO(HttpStatus.BAD_REQUEST.value(), "Age must be grater than 16 years.", dto);
            }
        } else {
            return new CommonResponseDTO(HttpStatus.NOT_FOUND.value(), "This Athlete Cannot found", null);
        }
    }

}
