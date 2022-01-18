package com.adidas.subscriptionservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_adidas_subscription", schema = "adidas")
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Integer id;

    @Column(name="email", nullable = false, length = 30)
    private String email;

    @Column(name="name", nullable = true, length = 30)
    private String name;

    @Column(name="gender", nullable = true, length = 6)
    private String gender;

    @Column(name="birthdate", nullable = false)
    private String birthdate;

    @Column(name="consent", nullable = false)
    private Boolean consent;

}