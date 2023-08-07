package ru.shareit.need;

public class NeedMapper {

    public static Need dtoToPojo(NeedDto dto) {
        return Need.builder()
                .description(dto.getDescription())
                .build();
    }

    public static NeedDto pojoToDto(Need pojo) {
        return NeedDto.builder()
                .description(pojo.getDescription())
                .build();
    }
}
