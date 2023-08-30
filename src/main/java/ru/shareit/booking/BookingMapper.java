package ru.shareit.booking;

public class BookingMapper {

    public static Booking dtoToPojo(BookingDto dto) {
        Booking pojo = new Booking();
        pojo.setStart(dto.getStart());
        pojo.setFinish(dto.getFinish());
        pojo.setFeedback(dto.getFeedback());
        return pojo;
    }

    public static BookingDto pojoToDto(Booking pojo) {
        BookingDto dto = new BookingDto();
        dto.setStart(pojo.getStart());
        dto.setFinish(pojo.getFinish());
        dto.setFeedback(pojo.getFeedback());
        return dto;
    }
}
