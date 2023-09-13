package ru.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shareit.exception.AccessException;
import ru.shareit.exception.NoSuchItemException;
import ru.shareit.item.Item;
import ru.shareit.item.ItemRepository;
import ru.shareit.item.ItemService;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;
import ru.shareit.user.UserService;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional
public class BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private UserService userService;
    private ItemService itemService;

    public BookingService(@Qualifier("bookingRepositoryJPA") BookingRepository bookingRepository,
                          @Qualifier("userRepositoryJPA") UserRepository userRepository,
                          @Qualifier("itemRepositoryJPA") ItemRepository itemRepository,
                          UserService userService,
                          ItemService itemService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public BookingDtoOut getBooking(long bookingId, long userId){
        checkBookingById(bookingId);
        userService.checkUserById(userId);

        User user = userRepository.getById(userId).get();
        Booking booking = bookingRepository.getById(bookingId).get();
        if (!booking.getRenter().equals(user) && !booking.getItem().getOwner().equals(user))
            throw new AccessException("Бронирование могут просматривать только арендатор и арендодатель");

        return BookingMapper.pojoToDto(booking);
    }

    public BookingDtoOut createBooking(BookingDtoIn bookingDtoIn, long userId, long itemId){
        userService.checkUserById(userId);
        itemService.checkItemById(itemId);

        LocalDate start = bookingDtoIn.getStart();
        LocalDate finish = bookingDtoIn.getFinish();
        long crossBookingsCount = bookingRepository.checkCrossingBookings(start, finish);
        if (crossBookingsCount > 0) throw new AccessException("Ваша бронь пересекается с чьей-то еще");

        Booking booking = BookingMapper.dtoToPojo(bookingDtoIn);

        Item item = itemRepository.getById(itemId).get();
        if (!item.getAvailable()) throw new NoSuchItemException("Вещь пока недоступна для аренды");
        booking.setItem(item);
        User renter = userRepository.getById(userId).get();
        booking.setRenter(renter);

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Создано " + savedBooking);
        return BookingMapper.pojoToDto(savedBooking);
    }

    public void cancelBooking(long bookingId, long userId){
        checkBookingById(bookingId);
        userService.checkUserById(userId);

        User user = userRepository.getById(userId).get();
        Booking booking = bookingRepository.getById(bookingId).get();
        if (!booking.getRenter().equals(user))
            throw new AccessException("Бронирование может отменить только арендатор");
        if (!booking.getStatus().equals(Status.WAITING))
            throw new AccessException("Бронирование не может быть изменено");

        booking.setStatus(Status.CANCELED);
        bookingRepository.save(booking);
        log.info("Бронирование " + bookingId + " отменено");
    }

    public void approveOrRejectBooking(long bookingId, long userId, boolean isApproved){
        checkBookingById(bookingId);
        userService.checkUserById(userId);

        User user = userRepository.getById(userId).get();
        Booking booking = bookingRepository.getById(bookingId).get();
        if (!booking.getItem().getOwner().equals(user))
            throw new AccessException("Согласие или отказ в брони может дать только владелец вещи");
        if (!booking.getStatus().equals(Status.WAITING))
            throw new AccessException("Бронирование не может быть изменено");

        if (isApproved) {
            booking.setStatus(Status.APPROVED);
            bookingRepository.save(booking);
            log.info("Бронирование " + bookingId + " подтверждено");
        }
        else {
            booking.setStatus(Status.REJECTED);
            bookingRepository.save(booking);
            log.info("Бронирование " + bookingId + " не подтверждено");
        }
    }

    public void checkBookingById(long id) {
        if (!bookingRepository.existsById(id)) {
            throw new NoSuchItemException("Не найдено бронирование " + id);
        }
    }
}
