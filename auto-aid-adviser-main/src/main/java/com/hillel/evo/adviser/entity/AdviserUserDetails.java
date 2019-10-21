package com.hillel.evo.adviser.entity;

import com.hillel.evo.adviser.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "adviser_usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdviserUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
