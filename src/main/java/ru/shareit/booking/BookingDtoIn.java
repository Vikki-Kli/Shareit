package ru.shareit.booking;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BookingDtoIn {

    @FutureOrPresent
    private LocalDate start;
    @FutureOrPresent
    private LocalDate finish;

}
