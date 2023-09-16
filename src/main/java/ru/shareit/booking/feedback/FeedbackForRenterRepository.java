package ru.shareit.booking.feedback;

import org.springframework.data.repository.NoRepositoryBean;
import ru.shareit.user.User;

import java.util.List;

@NoRepositoryBean
public interface FeedbackForRenterRepository {

    FeedbackForRenter save(FeedbackForRenter feedback);
    List<FeedbackForRenter> findAllByBooking_renterEquals(User user);
}
