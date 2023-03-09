package com.vitalhub.ams.athletesmanagementapi.service;

import com.vitalhub.ams.athletesmanagementapi.dto.request.AthleteRequestDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.request.SearchAthleteRequestDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.response.CommonResponseDTO;

public interface AthleteService {
    public CommonResponseDTO addAthlete(AthleteRequestDTO dto);

    public CommonResponseDTO searchAthlete(SearchAthleteRequestDTO dto);

    CommonResponseDTO getAthlete(String athleteId) throws Exception;

    CommonResponseDTO updateAthlete(String id, AthleteRequestDTO dto) throws Exception;
}
