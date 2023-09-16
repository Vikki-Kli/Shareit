package ru.shareit.booking.feedback;

import org.springframework.data.repository.NoRepositoryBean;
import ru.shareit.item.Item;

import java.util.List;

@NoRepositoryBean
public interface FeedbackForItemRepository {

    FeedbackForItem save(FeedbackForItem feedback);
    List<FeedbackForItem> findAllByBooking_itemEquals(Item item);
}
