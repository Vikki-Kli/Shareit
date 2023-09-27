package ru.shareit.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDtoOut {

    @FutureOrPresent
    private LocalDate start;
    @FutureOrPresent
    private LocalDate finish;
    @NotNull
    private Status status;
    @NotBlank
    private String itemName;
    @NotBlank
    private String renterName;

}
