package com.hillel.evo.adviser.userprofile.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SimpleUserRegistrationDto extends UserRegistrationDto {
    // здесь в дальнейшем будут поля свойственные только регистрации обычного пользователя
}
