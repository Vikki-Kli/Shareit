package ru.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.shareit.exception.NoSuchUserException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
@Lazy
@Slf4j
public class UserRepositoryInMemory implements UserRepository {

    Map<Long, User> users = new HashMap<>();
    private static long countId = 0;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> findById(long id) {
        if (!existsById(id)) throw new NoSuchUserException("Пользователь " + id + " не существует");
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            countId++;
            user.setId(countId);
            log.info("{} has been created", user);
        }
        users.put(user.getId(), user);
        log.info("{} has been edited", user);
        return findById(user.getId()).get();
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) throw new NoSuchUserException("Пользователь " + id + " не существует");
        users.remove(id);
        log.info("{} has been deleted", id);
    }

    @Override
    public boolean existsById(long id) {
        return users.containsKey(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

}
