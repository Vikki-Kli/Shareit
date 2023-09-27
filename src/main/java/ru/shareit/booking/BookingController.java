package ru.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{id}")
    public BookingDtoOut getBooking(@PathVariable long id,
                                    @RequestHeader("X-ShareIt-User-Id") long userId) {
        return bookingService.getBooking(id, userId);
    }

    @PostMapping()
    public BookingDtoOut createBooking(@Valid @RequestBody BookingDtoIn booking,
                                       @RequestHeader("X-ShareIt-User-Id") long userId,
                                       @RequestParam("item") long itemId) {
        return bookingService.createBooking(booking, userId, itemId);
    }

    @PatchMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable long id,
                                @RequestHeader("X-ShareIt-User-Id") long userId) {
        bookingService.cancelBooking(id, userId);
        return "Booking " + id + " has been canceled";
    }

    @PatchMapping("/{id}")
    public String approveOrRejectBooking(@PathVariable long id,
                                         @RequestHeader("X-ShareIt-User-Id") long userId,
                                         @RequestParam("approved") boolean isApproved) {
        bookingService.approveOrRejectBooking(id, userId, isApproved);
        if (isApproved) return "Booking " + id + " has been approved";
        else return "Booking " + id + " has been rejected";
    }

    @PostMapping("/{bookingId}/feedback")
    public String addFeedbackForItem(@Valid @RequestBody String text,
                                       @RequestHeader("X-ShareIt-User-Id") long userId,
                                       @PathVariable long bookingId) {
        bookingService.addFeedback(text, userId, bookingId);
        return "Your feedback has been added successfully";
    }

    @GetMapping("/item_feedbacks/{id}")
    public List<String> getFeedbacksForItem(@PathVariable long id) {
        return bookingService.getFeedbacksForItem(id);
    }

    @GetMapping("/renter_feedbacks/{id}")
    public List<String> getFeedbacksForRenter(@PathVariable long id) {
        return bookingService.getFeedbacksForRenter(id);
    }
}
