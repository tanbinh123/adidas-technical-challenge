package com.adidas.publicservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseDTO {

    private HttpStatus responseCode;
    private String responseMessage;

}
