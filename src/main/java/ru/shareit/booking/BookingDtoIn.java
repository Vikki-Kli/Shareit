package ru.shareit.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
public class BookingDtoIn {

    @FutureOrPresent
    @NotNull
    private LocalDate start;
    @FutureOrPresent
    @NotNull
    private LocalDate finish;

}
