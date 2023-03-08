package com.vitalhub.ams.athletesmanagementapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseDTO {
    private int code;
    private String message;
    private Object data;
}
