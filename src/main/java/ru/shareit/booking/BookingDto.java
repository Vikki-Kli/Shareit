package ru.shareit.booking;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingDto {

    @FutureOrPresent
    private LocalDate start;
    @FutureOrPresent
    private LocalDate finish;
    private String feedback = "";

}
