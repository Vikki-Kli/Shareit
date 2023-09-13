package ru.shareit.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private boolean available;
}
