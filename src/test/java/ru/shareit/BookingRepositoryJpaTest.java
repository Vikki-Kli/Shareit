package ru.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.shareit.booking.BookingRepositoryJPA;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class BookingRepositoryJpaTest {

    @Autowired
    private BookingRepositoryJPA repository;

    @Test
    @Sql("classpath:data.sql")
    public void checkCrossingBookings_regular() {
        LocalDate start = LocalDate.of(2023, 1,1);
        LocalDate finish = LocalDate.of(2023, 1,5);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,1);
        finish = LocalDate.of(2023, 1,4);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,1);
        finish = LocalDate.of(2023, 1,7);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,1);
        finish = LocalDate.of(2023, 1,10);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,1);
        finish = LocalDate.of(2023, 1,11);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,5);
        finish = LocalDate.of(2023, 1,9);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,5);
        finish = LocalDate.of(2023, 1,10);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,5);
        finish = LocalDate.of(2023, 1,11);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,6);
        finish = LocalDate.of(2023, 1,9);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,6);
        finish = LocalDate.of(2023, 1,10);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,6);
        finish = LocalDate.of(2023, 1,11);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,10);
        finish = LocalDate.of(2023, 1,11);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,11);
        finish = LocalDate.of(2023, 1,11);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,10);
        finish = LocalDate.of(2023, 1,10);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,7);
        finish = LocalDate.of(2023, 1,7);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,5);
        finish = LocalDate.of(2023, 1,5);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(1);

        start = LocalDate.of(2023, 1,4);
        finish = LocalDate.of(2023, 1,4);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,13);
        finish = LocalDate.of(2023, 1,15);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,13);
        finish = LocalDate.of(2023, 1,17);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,13);
        finish = LocalDate.of(2023, 1,20);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,13);
        finish = LocalDate.of(2023, 1,22);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,15);
        finish = LocalDate.of(2023, 1,17);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,15);
        finish = LocalDate.of(2023, 1,20);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,15);
        finish = LocalDate.of(2023, 1,22);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,17);
        finish = LocalDate.of(2023, 1,18);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,17);
        finish = LocalDate.of(2023, 1,20);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,17);
        finish = LocalDate.of(2023, 1,22);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,20);
        finish = LocalDate.of(2023, 1,22);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,15);
        finish = LocalDate.of(2023, 1,15);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,17);
        finish = LocalDate.of(2023, 1,17);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,20);
        finish = LocalDate.of(2023, 1,20);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(0);

        start = LocalDate.of(2023, 1,1);
        finish = LocalDate.of(2023, 1,30);
        assertThat(repository.checkCrossingBookings(start, finish)).isEqualTo(2);
    }
}
