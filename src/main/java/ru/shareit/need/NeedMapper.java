package ru.shareit.need;

public class NeedMapper {

    public static Need dtoToPojo(NeedDto dto) {
        Need pojo = new Need();
        pojo.setDescription(dto.getDescription());
        return pojo;
    }

    public static NeedDto pojoToDto(Need pojo) {
        NeedDto dto = new NeedDto();
        dto.setDescription(pojo.getDescription());
        return dto;
    }
}
