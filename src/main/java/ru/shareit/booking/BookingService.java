package ru.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shareit.booking.feedback.FeedbackForItem;
import ru.shareit.booking.feedback.FeedbackForItemRepository;
import ru.shareit.booking.feedback.FeedbackForRenter;
import ru.shareit.booking.feedback.FeedbackForRenterRepository;
import ru.shareit.exception.AccessException;
import ru.shareit.exception.NoSuchItemException;
import ru.shareit.item.Item;
import ru.shareit.item.ItemRepository;
import ru.shareit.item.ItemService;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;
import ru.shareit.user.UserService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final FeedbackForRenterRepository feedbackForRenterRepository;
    private final FeedbackForItemRepository feedbackForItemRepository;
    private final UserService userService;
    private final ItemService itemService;

    public BookingService(@Qualifier("bookingRepositoryJPA") BookingRepository bookingRepository,
                          @Qualifier("userRepositoryJPA") UserRepository userRepository,
                          @Qualifier("itemRepositoryJPA") ItemRepository itemRepository,
                          @Qualifier("feedbackForItemRepositoryJPA") FeedbackForItemRepository feedbackForItemRepository,
                          @Qualifier("feedbackForRenterRepositoryJPA") FeedbackForRenterRepository feedbackForRenterRepository,
                          UserService userService,
                          ItemService itemService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.feedbackForItemRepository = feedbackForItemRepository;
        this.feedbackForRenterRepository = feedbackForRenterRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public BookingDtoOut getBooking(long bookingId, long userId){
        User user = checkAndReturnUser(userId);
        Booking booking = checkAndReturnBooking(bookingId);

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

        Item item = itemRepository.findById(itemId).get();
        if (!item.getAvailable()) throw new NoSuchItemException("Вещь пока недоступна для аренды");
        booking.setItem(item);
        User renter = userRepository.findById(userId).get();
        booking.setRenter(renter);

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Создано " + savedBooking);
        return BookingMapper.pojoToDto(savedBooking);
    }

    public void cancelBooking(long bookingId, long userId){
        User user = checkAndReturnUser(userId);
        Booking booking = checkAndReturnBooking(bookingId);

        if (!booking.getRenter().equals(user))
            throw new AccessException("Бронирование может отменить только арендатор");
        if (!booking.getStatus().equals(Status.WAITING))
            throw new AccessException("Бронирование не может быть изменено");

        booking.setStatus(Status.CANCELED);
        bookingRepository.save(booking);
        log.info("Бронирование " + bookingId + " отменено");
    }

    public void approveOrRejectBooking(long bookingId, long userId, boolean isApproved){
        User user = checkAndReturnUser(userId);
        Booking booking = checkAndReturnBooking(bookingId);

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

    public void addFeedback(String text, long userId, long bookingId) {
        User user = checkAndReturnUser(userId);
        Booking booking = checkAndReturnBooking(bookingId);

        if (booking.getStart().isAfter(LocalDate.now()) || booking.getStatus() != Status.APPROVED)
            throw new AccessException("На эту бронь нельзя оставить отзыв");

        if (booking.getRenter().equals(user)) {
            FeedbackForItem feedback = new FeedbackForItem();
            feedback.setBooking(booking);
            feedback.setText(text);
            feedbackForItemRepository.save(feedback);
        }
        else if (booking.getItem().getOwner().equals(user)) {
            FeedbackForRenter feedback = new FeedbackForRenter();
            feedback.setBooking(booking);
            feedback.setText(text);
            feedbackForRenterRepository.save(feedback);
        }
        else throw new AccessException("Вы не можете добавлять отзывы к чужой брони");
    }

    public List<String> getFeedbacksForItem(long id) {
        Item item = checkAndReturnItem(id);
        return feedbackForItemRepository.findAllByBooking_itemEquals(item).stream().map(FeedbackForItem::getText).toList();
    }

    public List<String> getFeedbacksForRenter(long id) {
        User user = checkAndReturnUser(id);
        return feedbackForRenterRepository.findAllByBooking_renterEquals(user).stream().map(FeedbackForRenter::getText).toList();
    }

    private User checkAndReturnUser(long id) {
        userService.checkUserById(id);
        return userRepository.findById(id).get();
    }

    private Booking checkAndReturnBooking(long id) {
        checkBookingById(id);
        return bookingRepository.findById(id).get();
    }

    private Item checkAndReturnItem(long id) {
        itemService.checkItemById(id);
        return itemRepository.findById(id).get();
    }
}
