package ru.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String name;
    @Email
    private String email;

}
