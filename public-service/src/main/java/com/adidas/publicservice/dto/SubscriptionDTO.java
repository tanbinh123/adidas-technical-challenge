package com.adidas.publicservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {

    @NotNull(message = "Email is a mandatory field")
    @Email
    @Size(max = 30)
    private String email;

    @Size(max = 30)
    @Pattern(regexp = "[A-Za-z]")
    private String name;

    @Size(max = 6)
    @Pattern(regexp = "[A-Za-z]")
    private String gender;

    @NotNull(message = "Birthdate is a mandatory field")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthdate;

    @NotNull(message = "Consent is a mandatory field")
    private Boolean consent;

}
