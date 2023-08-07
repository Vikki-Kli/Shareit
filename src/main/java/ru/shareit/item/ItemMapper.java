package ru.shareit.item;

public class ItemMapper {

    public static Item dtoToPojo(ItemDto dto) {
        return Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .build();
    }

    public static ItemDto pojoToDto(Item pojo) {
        return ItemDto.builder()
                .name(pojo.getName())
                .description(pojo.getDescription())
                .available(pojo.getAvailable())
                .build();
    }
}
