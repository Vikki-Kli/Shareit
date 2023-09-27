package ru.shareit.need;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
public class NeedDto {

    @NotBlank
    private String description;
}
