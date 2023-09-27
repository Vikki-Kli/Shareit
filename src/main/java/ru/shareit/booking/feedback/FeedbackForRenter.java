package ru.shareit.booking.feedback;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.shareit.booking.Booking;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks_for_renters")
public class FeedbackForRenter implements Feedback{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="booking_id")
    @NotNull
    private Booking booking;
    @NotBlank
    private String text;
}
