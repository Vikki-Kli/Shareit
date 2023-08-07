package ru.shareit.user;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();
    User getUser(long id);
    User createUser(User user);
    User editUser(User user, long id);
    void deleteUser(long id);
}
