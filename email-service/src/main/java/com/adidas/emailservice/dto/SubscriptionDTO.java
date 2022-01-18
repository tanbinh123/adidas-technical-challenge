package com.adidas.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {

    public Integer id;
    private String email;
    private String name;
    private String gender;
    private String birthdate;
    private Boolean consent;

}
