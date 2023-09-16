package ru.shareit.booking.feedback;

import jakarta.persistence.*;
import lombok.*;
import ru.shareit.booking.Booking;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "feedbacks_for_renters")
public class FeedbackForRenter implements Feedback{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="booking_id")
    private Booking booking;
    private String text;
}
