package ru.shareit.need;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NeedDto {

    @NotBlank
    private String description;
}
