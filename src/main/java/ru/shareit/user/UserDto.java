package ru.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;

}
