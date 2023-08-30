package ru.shareit.user;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepository {
    Collection<User> findAll();
    Optional<User> getById(long id);
    User save(User user);
    void deleteById(long id);
    boolean existsById(long id);
}
