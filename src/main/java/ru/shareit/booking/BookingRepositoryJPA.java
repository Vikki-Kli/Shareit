package ru.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookingRepositoryJPA extends BookingRepository, JpaRepository<Booking, Long>{

    @Query("SELECT COUNT(*) " +
            "FROM Booking b " +
            "WHERE b.start <= ?2 " +
            "AND b.finish >= ?1 " +
            "AND b.status IN (WAITING, APPROVED)")
    long checkCrossingBookings(LocalDate start, LocalDate finish);
}
