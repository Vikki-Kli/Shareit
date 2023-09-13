package ru.shareit.booking;

import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.Optional;

@NoRepositoryBean
public interface BookingRepository {

    boolean existsById(long id);
    Optional<Booking> getById(long id);
    Booking save(Booking booking);
    long checkCrossingBookings(LocalDate start, LocalDate finish);

}
