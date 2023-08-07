package ru.shareit.booking;

public class BookingMapper {

    public static Booking dtoToPojo(BookingDto dto) {
        return Booking.builder()
                .start(dto.getStart())
                .finish(dto.getFinish())
                .feedback(dto.getFeedback())
                .build();
    }

    public static BookingDto pojoToDto(Booking pojo) {
        return BookingDto.builder()
                .start(pojo.getStart())
                .finish(pojo.getFinish())
                .feedback(pojo.getFeedback())
                .build();
    }
}
