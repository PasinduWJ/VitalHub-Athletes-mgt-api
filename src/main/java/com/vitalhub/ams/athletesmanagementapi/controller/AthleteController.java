package com.vitalhub.ams.athletesmanagementapi.controller;

import com.vitalhub.ams.athletesmanagementapi.dto.request.AthleteRequestDTO;
import com.vitalhub.ams.athletesmanagementapi.dto.response.CommonResponseDTO;
import com.vitalhub.ams.athletesmanagementapi.service.AthleteService;
import com.vitalhub.ams.athletesmanagementapi.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/athlete")
public class AthleteController {

    private final AthleteService athleteService;

    @Autowired
    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<StandardResponse> addAthlete(
            @RequestBody AthleteRequestDTO dto
//            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        StandardResponse response = null;
        try {
            CommonResponseDTO responseDTO = athleteService.addAthlete(dto);
            response = new StandardResponse(
                    responseDTO.getCode(),
                    responseDTO.getMessage(),
                    responseDTO.getData());
        } catch (Exception e) {
            response = new StandardResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null);
        } finally {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/find", params = "id")
    public ResponseEntity<StandardResponse> addAthlete(@RequestParam String id) {
        StandardResponse response = null;
        try {
            CommonResponseDTO responseDTO = athleteService.getAthlete(id);
            response = new StandardResponse(
                    responseDTO.getCode(),
                    responseDTO.getMessage(),
                    responseDTO.getData());
        } catch (Exception e) {
            response = new StandardResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null);
        } finally {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<StandardResponse> addAthlete() {
        StandardResponse response = null;
        try {
            CommonResponseDTO responseDTO = athleteService.getAllAthlete();
            response = new StandardResponse(
                    responseDTO.getCode(),
                    responseDTO.getMessage(),
                    responseDTO.getData());
        } catch (Exception e) {
            response = new StandardResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null);
        } finally {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}
