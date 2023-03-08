package com.vitalhub.ams.athletesmanagementapi.controller;

import com.vitalhub.ams.athletesmanagementapi.service.CommonService;
import com.vitalhub.ams.athletesmanagementapi.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/common")
public class CommonController {

    private final CommonService commonService;

    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping(value = "/getall")
    public ResponseEntity<StandardResponse> getAllCommonData() {
        StandardResponse response = null;
        try {
            Map<String, Object> mapData = new HashMap<>();
            mapData.put("country", commonService.getAllCountry());
            mapData.put("gender", commonService.getAllGender());
            mapData.put("event", commonService.getAllEvent());

            response = new StandardResponse(
                    HttpStatus.OK.value(),
                    "All Page initial Data",
                    mapData);
        } catch (Exception e) {
            response = new StandardResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null);
        } finally {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
