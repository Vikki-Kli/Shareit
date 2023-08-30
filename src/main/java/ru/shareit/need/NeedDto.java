package ru.shareit.need;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class NeedDto {

    @NotBlank
    private String description;
}
