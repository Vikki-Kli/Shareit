package ru.shareit.item;

public class ItemMapper {

    public static Item dtoToPojo(ItemDto dto) {
        Item pojo = new Item();
        pojo.setName(dto.getName());
        pojo.setDescription(dto.getDescription());
        pojo.setAvailable(dto.isAvailable());
        return pojo;
    }

    public static ItemDto pojoToDto(Item pojo) {
        ItemDto dto = new ItemDto();
        dto.setName(pojo.getName());
        dto.setDescription(pojo.getDescription());
        dto.setAvailable(pojo.getAvailable());
        return dto;
    }
}
