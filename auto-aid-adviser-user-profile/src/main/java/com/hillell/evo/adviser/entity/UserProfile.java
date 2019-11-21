package com.hillell.evo.adviser.entity;

import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.entity.SimpleUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "user_profile")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class UserProfile {
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private SimpleUser simpleUser;
//    подумать над даними полями
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user_profile")
    private Set<ServiceForBusiness> typeCar;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user_profile")
    private Set<ServiceForBusiness> carBrand;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user_profile")
    private Set<ServiceForBusiness> motorType;
}
