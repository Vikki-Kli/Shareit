package ru.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shareit.exception.NoSuchUserException;

import java.util.Collection;

@Service
@Slf4j
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(@Qualifier("userRepositoryJPA") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::pojoToDto).toList();
    }

    public UserDto getUser(long id) {
        checkUserById(id);
        return UserMapper.pojoToDto(userRepository.findById(id).get());
    }

    public UserDto createUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.dtoToPojo(userDto));
        log.info("Создан пользователь " + user);
        return UserMapper.pojoToDto(user);
    }

    public UserDto editUser(UserDto userDto, long id) {
        checkUserById(id);
        User user = UserMapper.dtoToPojo(userDto);
        user.setId(id);
        User savedUser = userRepository.save(user);
        log.info("Изменен пользователь " + savedUser);
        return UserMapper.pojoToDto(savedUser);
    }

    public void deleteUser(long id) {
        checkUserById(id);
        userRepository.deleteById(id);
        log.info("Удален пользователь " + id);
    }

    public void checkUserById(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException("Не найден пользователь " + userId);
        }
    }
}
