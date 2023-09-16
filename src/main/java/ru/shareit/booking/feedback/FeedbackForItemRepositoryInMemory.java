package ru.shareit.booking.feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.shareit.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Lazy
@Slf4j
public class FeedbackForItemRepositoryInMemory implements FeedbackForItemRepository{

    Map<Long, FeedbackForItem> feedbacks = new HashMap<>();
    private static long countId = 0;

    @Override
    public FeedbackForItem save(FeedbackForItem feedback) {
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
    public List<FeedbackForItem> findAllByBooking_itemEquals(Item item) {
        return feedbacks.values().stream().filter(s -> s.getBooking().getItem().equals(item)).toList();
    }
}
