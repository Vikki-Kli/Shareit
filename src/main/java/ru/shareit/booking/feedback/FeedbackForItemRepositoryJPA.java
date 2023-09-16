package ru.shareit.booking.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackForItemRepositoryJPA extends FeedbackForItemRepository, JpaRepository<FeedbackForItem, Long> {

}
