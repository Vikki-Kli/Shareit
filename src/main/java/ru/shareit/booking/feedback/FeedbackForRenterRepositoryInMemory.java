package ru.shareit.booking.feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.shareit.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Lazy
@Slf4j
public class FeedbackForRenterRepositoryInMemory implements FeedbackForRenterRepository{

    Map<Long, FeedbackForRenter> feedbacks = new HashMap<>();
    private static long countId = 0;

    @Override
    public FeedbackForRenter save(FeedbackForRenter feedback) {
        if (feedback.getId() == null) {
            countId++;
            feedback.setId(countId);
            log.info("{} has been created", feedback);
        }
        feedbacks.put(feedback.getId(), feedback);
        log.info("{} has been saved", feedback);
        return feedback;
    }

    @Override
    public List<FeedbackForRenter> findAllByBooking_renterEquals(User user) {
        return feedbacks.values().stream().filter(s -> s.getBooking().getRenter().equals(user)).toList();
    }
}
