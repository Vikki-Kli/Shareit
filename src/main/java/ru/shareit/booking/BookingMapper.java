package ru.shareit.booking;

public class BookingMapper {

    public static Booking dtoToPojo(BookingDtoIn dto) {
        Booking pojo = new Booking();
        pojo.setStart(dto.getStart());
        pojo.setFinish(dto.getFinish());
        return pojo;
    }

    public static BookingDtoOut pojoToDto(Booking pojo) {
        BookingDtoOut dto = new BookingDtoOut();
        dto.setStart(pojo.getStart());
        dto.setFinish(pojo.getFinish());
        dto.setStatus(pojo.getStatus());
        dto.setItemName(pojo.getItem().getName());
        dto.setRenterName(pojo.getRenter().getName());
        return dto;
    }
}
