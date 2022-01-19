package com.adidas.publicservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    @NotNull(message = "Email is a mandatory field")
    @Email(message = "Invalid format, it must be an email")
    @Size(min=1, max = 30, message = "String size must be between 1 and 30 characters")
    private String email;

    @Size(min=1, max=30, message = "String size must be between 1 and 30 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Only letters allowed.")
    private String name;

    @Size(min=1, max=6, message = "String size must be between 1 and 6 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Only letters allowed.")
    private String gender;

    @NotNull(message = "Birthdate is a mandatory field")
    @Pattern(regexp = "^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$", message = "Date format invalid, it must be dd-mm-yyyy")
    private String birthdate;

    @NotNull(message = "Consent is a mandatory field")
    private Boolean consent;

}
