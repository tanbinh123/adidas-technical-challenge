package com.adidas.subscriptionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseDTO {

    private HttpStatus responseCode;
    private Object response;

}
