package ru.jafix.fruties.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.jafix.fruties.entities.Role;

@Getter
@Setter
@AllArgsConstructor
public class JwtDto {
    protected String jwt;
    protected Role role;
}
