package ru.shareit.item;

import org.springframework.data.repository.NoRepositoryBean;
import ru.shareit.user.User;

import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public interface ItemRepository {
    Collection<Item> findAllByOwner(User user);
    Collection<Item> findByDescriptionContainingIgnoreCase(String text);
    Optional<Item> getById(long id);
    Item save(Item item);
    void deleteById(long id);
    boolean existsById(long id);
}
