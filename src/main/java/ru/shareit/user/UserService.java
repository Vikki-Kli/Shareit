package ru.shareit.user;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    private UserRepositoryInMemory userRepository;

    public UserService(UserRepositoryInMemory userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::pojoToDto).toList();
    }

    public UserDto getUser(long id) {
        return UserMapper.pojoToDto(userRepository.getUser(id));
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.dtoToPojo(userDto);
        return UserMapper.pojoToDto(userRepository.createUser(user));
    }

    public UserDto editUser(UserDto userDto, long id) {
        User user = UserMapper.dtoToPojo(userDto);
        return UserMapper.pojoToDto(userRepository.editUser(user, id));
    }

    public void deleteUser(long id) {
        userRepository.deleteUser(id);
    }
}
