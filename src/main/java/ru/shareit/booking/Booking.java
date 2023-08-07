package ru.shareit.booking;

import lombok.Builder;
import lombok.Data;
import ru.shareit.item.Item;
import ru.shareit.user.User;

import java.time.LocalDate;

@Data
@Builder
public class Booking {

    private Long id;
    private LocalDate start;
    private LocalDate finish;
    private Item item;
    private User renter;
    private String feedback;

}
