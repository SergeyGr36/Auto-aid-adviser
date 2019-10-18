package com.hillel.evo.adviser.registration.entity;

import com.hillel.evo.adviser.registration.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Email
    @NotNull
    @Column(name = "mail", unique = true, nullable = false)
    private String mail;

    @NotNull
    private String password;

    private boolean active = false;

    private String activationCode;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleUser role = RoleUser.ROLE_USER;
}
