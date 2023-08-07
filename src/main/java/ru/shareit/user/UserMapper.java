package ru.shareit.user;

public class UserMapper {

    public static User dtoToPojo(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public static UserDto pojoToDto(User pojo) {
        return UserDto.builder()
                .name(pojo.getName())
                .email(pojo.getEmail())
                .build();
    }
}
