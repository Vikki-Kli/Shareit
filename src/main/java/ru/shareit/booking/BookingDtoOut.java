package ru.shareit.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
