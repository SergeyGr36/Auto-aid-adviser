package com.hillel.evo.adviser.entity;

import com.hillel.evo.adviser.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "adviser_usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class AdviserUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    private String password;

    private boolean active = false;

    private String activationCode;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleUser role = RoleUser.ROLE_USER;
}