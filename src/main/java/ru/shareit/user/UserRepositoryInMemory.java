package ru.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.shareit.exception.NoSuchUserException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Repository
@Slf4j
public class UserRepositoryInMemory implements UserRepository {

    Map<Long, User> users = new HashMap<>();
    private static long countId = 0;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User getUser(long id) {
        if (!users.containsKey(id)) throw new NoSuchUserException("Пользователь " + id + " не существует");
        return users.get(id);
    }

    @Override
    public User createUser(User user) {
        countId++;
        user.setId(countId);
        users.put(countId, user);
        log.info("{} has been created", user);
        return getUser(countId);
    }

    @Override
    public User editUser(User user, long id) {
        if (!users.containsKey(id)) throw new NoSuchUserException("Пользователь " + id + " не существует");
        user.setId(id);
        users.put(id, user);
        log.info("{} has been edited", user);
        return getUser(id);
    }

    @Override
    public void deleteUser(long id) {
        if (!users.containsKey(id)) throw new NoSuchUserException("Пользователь " + id + " не существует");
        users.remove(id);
        log.info("{} has been deleted", id);
    }

}
