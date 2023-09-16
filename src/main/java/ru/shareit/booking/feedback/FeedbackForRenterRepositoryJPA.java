package ru.shareit.booking.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackForRenterRepositoryJPA extends FeedbackForRenterRepository, JpaRepository<FeedbackForRenter, Long> {
}
