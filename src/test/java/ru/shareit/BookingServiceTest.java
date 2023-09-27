package ru.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shareit.booking.*;
import ru.shareit.booking.feedback.FeedbackForItemRepository;
import ru.shareit.booking.feedback.FeedbackForRenterRepository;
import ru.shareit.exception.AccessException;
import ru.shareit.item.Item;
import ru.shareit.item.ItemRepository;
import ru.shareit.item.ItemService;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;
import ru.shareit.user.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    FeedbackForRenterRepository feedbackForRenterRepository;
    @Mock
    FeedbackForItemRepository feedbackForItemRepository;
    @Mock
    UserService userService;
    @Mock
    ItemService itemService;

    @Test
    public void getBooking_regular() {
        User user = new User(1L,"Kolya","qwerty@mail.ru");
        Item item = new Item(1L, user, "Cat", "Pussy hsssss!", true, new HashSet<>());
        Booking booking = new Booking(1L, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 3), item, user, Status.WAITING);
        BookingDtoOut dto = new BookingDtoOut(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 3), Status.WAITING, "Cat", "Kolya");

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(bookingRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        assertThat(bookingService.getBooking(1L, 1L)).isEqualTo(dto);
    }

    @Test
    public void getBooking_throwsAccessException() {
        User owner = new User(1L,"Kolya","qwerty@mail.ru");
        User renter = new User(2L,"Pasha","12345@mail.ru");
        User dude = new User(3L,"Zheka","ololo@mail.ru");
        Item item = new Item(1L, owner, "Cat", "Pussy hsssss!", true, new HashSet<>());
        Booking booking = new Booking(1L, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 3), item, renter, Status.WAITING);

        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(dude));
        Mockito.when(bookingRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        assertThrows(AccessException.class, () -> bookingService.getBooking(1L, 3L));
    }

}
