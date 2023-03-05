package com.vitalhub.ams.athletesmanagementapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse {
    private HttpStatus code;
    private String message;
    private Object data;

}
