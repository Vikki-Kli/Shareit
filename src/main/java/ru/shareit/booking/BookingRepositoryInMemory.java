package ru.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.shareit.exception.NoSuchItemException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Lazy
@Slf4j
public class BookingRepositoryInMemory implements BookingRepository{

    Map<Long, Booking> bookings = new HashMap<>();
    private static long countId = 0;

    @Override
    public Optional<Booking> findById(long id) {
        if (!bookings.containsKey(id)) throw new NoSuchItemException("Бронирование " + id + " не существует");
        return Optional.ofNullable(bookings.get(id));
    }

    @Override
    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            countId++;
            booking.setId(countId);
            log.info("{} has been created", booking);
        }
        bookings.put(booking.getId(), booking);
        log.info("{} has been saved", booking);
        return findById(booking.getId()).get();
    }

    @Override
    public long checkCrossingBookings(LocalDate start, LocalDate finish) {
        return bookings.values().stream()
                .filter(s -> s.getStatus() == Status.APPROVED || s.getStatus() == Status.WAITING)
                .filter(s -> (s.getStart().isBefore(finish) || s.getStart().isEqual(finish))
                        && (s.getFinish().isAfter(start) || s.getFinish().isEqual(start)))
                .count();
    }

    @Override
    public boolean existsById(long id) {
        return bookings.containsKey(id);
    }
}
