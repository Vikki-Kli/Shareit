package ru.shareit.user;

public class UserMapper {

    public static User dtoToPojo(UserDto dto) {
        User pojo = new User();
        pojo.setName(dto.getName());
        pojo.setEmail(dto.getEmail());
        return pojo;
    }

    public static UserDto pojoToDto(User pojo) {
        UserDto dto = new UserDto();
        dto.setName(pojo.getName());
        dto.setEmail(pojo.getEmail());
        return dto;
    }
}
