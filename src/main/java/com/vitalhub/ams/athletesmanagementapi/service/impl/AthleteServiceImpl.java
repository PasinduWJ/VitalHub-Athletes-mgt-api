package com.vitalhub.ams.athletesmanagementapi.service.impl;

import com.vitalhub.ams.athletesmanagementapi.dto.ProfileImageDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.request.AthleteRequestDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.response.AthleteResponseDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.response.CommonResponseDTO;
import com.vitalhub.ams.athletesmanagementapi.entity.Athlete;
import com.vitalhub.ams.athletesmanagementapi.entity.AthleteEvent;
import com.vitalhub.ams.athletesmanagementapi.entity.Event;
import com.vitalhub.ams.athletesmanagementapi.entity.ProfileImage;
import com.vitalhub.ams.athletesmanagementapi.repository.AthleteRepository;
import com.vitalhub.ams.athletesmanagementapi.service.AthleteService;
import com.vitalhub.ams.athletesmanagementapi.util.FileCompress;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AthleteServiceImpl implements AthleteService {

    private final AthleteRepository athleteRepository;
    private final FileCompress fileCompress;
    private final ModelMapper modelMapper;

    @Autowired
    public AthleteServiceImpl(AthleteRepository athleteRepository, FileCompress fileCompress, ModelMapper modelMapper) {
        this.athleteRepository = athleteRepository;
        this.fileCompress = fileCompress;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommonResponseDTO addAthlete(AthleteRequestDTO dto) {

        LocalDate dobLocal = dto.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (Period.between(dobLocal, LocalDate.now()).getYears() > 16 && !dto.getEventIdList().isEmpty()) {  //todo

            Athlete athlete = modelMapper.map(dto, Athlete.class);
            athlete.setAthleteId("000");

            Optional<Athlete> existingAthlete = athleteRepository.findAthleteByFirstNameAndLastNameAndDobAndGenderAndCountry(
                    athlete.getFirstName(), athlete.getLastName(), athlete.getDob(), athlete.getGender(), athlete.getCountry());

            if (existingAthlete.isPresent()) {
                return new CommonResponseDTO(HttpStatus.BAD_REQUEST, "The Athlete already exist.", dto);
            }
            try {
                ProfileImage image = athlete.getProfileImage();
                if (dto.getProfileImage() != null && dto.getProfileImage().getImageData() != "") {
                    image.setImageData(Base64.getDecoder().decode(dto.getProfileImage().getImageData()));
                    athlete.setProfileImage(image);
                }else{
                    athlete.setProfileImage(null);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
            athlete.setCreated(new Date());
            athlete.setLastUpdate(new Date());

            athlete.setAthleteEvents(dto.getEventIdList().stream()
                    .map(val -> new AthleteEvent(athlete, new Event(val))).collect(Collectors.toSet()));

            athleteRepository.save(athlete);
            return new CommonResponseDTO(HttpStatus.CREATED, "Athlete added Successfully.", dto);
        } else {
            return new CommonResponseDTO(HttpStatus.BAD_REQUEST, "Age must be grater than 16 years.", dto);
        }

    }

    @Override
    public CommonResponseDTO getAthlete(String athleteId) {

        Optional<Athlete> athlete = athleteRepository.findById(athleteId);
        if (athlete.isPresent()) {
            AthleteResponseDTO responseDTO = modelMapper.map(athlete, AthleteResponseDTO.class);

            ProfileImageDTO image = modelMapper.map(athlete.get().getProfileImage() ,ProfileImageDTO.class) ;
            if (athlete.get().getProfileImage() != null && athlete.get().getProfileImage().getImageData().length > 0) {
                image.setImageData(Base64.getEncoder().encodeToString(athlete.get().getProfileImage().getImageData()));
                responseDTO.setProfileImage(image);
            }else{
                responseDTO.setProfileImage(null);
            }
            return new CommonResponseDTO(HttpStatus.OK, athleteId, responseDTO);
        } else {
            return new CommonResponseDTO(HttpStatus.NOT_FOUND, "Athlete Cannot found", athleteId);
        }
    }

    @Override
    public CommonResponseDTO getAllAthlete() {
        List<Athlete> athleteList = athleteRepository.findAll();
        if (!athleteList.isEmpty()) {
            List<AthleteResponseDTO> responseDTOList = athleteList.stream()
                    .map(athlete -> {
                        AthleteResponseDTO responseDTO = modelMapper.map(athlete, AthleteResponseDTO.class);
                        ProfileImageDTO image = modelMapper.map(athlete.getProfileImage(),ProfileImageDTO.class) ;
                        if (athlete.getProfileImage() != null && athlete.getProfileImage().getImageData().length > 0) {
                            image.setImageData(Base64.getEncoder().encodeToString(athlete.getProfileImage().getImageData()));
                            responseDTO.setProfileImage(image);
                        }else{
                            responseDTO.setProfileImage(null);
                        }
                        return responseDTO;
                    }).collect(Collectors.toList());
            return new CommonResponseDTO(HttpStatus.OK, "All athletes Details", responseDTOList);
        } else {
            return new CommonResponseDTO(HttpStatus.NOT_FOUND, "Athlete Cannot found", null);
        }
    }


}
