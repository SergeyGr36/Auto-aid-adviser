package com.hillel.evo.adviser.entity;

import com.hillel.evo.adviser.entity.SimpleUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;

@Data
@Entity(name = "user_profile")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private SimpleUser simpleUser;

}
