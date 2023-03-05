package com.vitalhub.ams.athletesmanagementapi.service;

import com.vitalhub.ams.athletesmanagementapi.dto.request.AthleteRequestDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.response.CommonResponseDTO;

public interface AthleteService {
    public CommonResponseDTO addAthlete(AthleteRequestDTO dto);
    public CommonResponseDTO getAthlete(String athleteId);
    public CommonResponseDTO getAllAthlete();
}
