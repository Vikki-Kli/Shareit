package ru.shareit.booking;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bookings")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

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
}
