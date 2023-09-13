package ru.shareit.booking;

import jakarta.persistence.*;
import lombok.*;
import ru.shareit.item.Item;
import ru.shareit.user.User;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate start;
    private LocalDate finish;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User renter;
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

}
