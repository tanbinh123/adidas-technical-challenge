package com.adidas.publicservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {

    private String email;
    private String name;
    private String gender;
    private Date birthdate;
    private Boolean consent;

}
