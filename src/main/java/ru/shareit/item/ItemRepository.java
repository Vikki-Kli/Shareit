package ru.shareit.item;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.shareit.user.User;

import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public interface ItemRepository {
    @RestResource(path="owner")
    Collection<Item> findAllByOwner(User user);
    @RestResource(path="contains")
    Collection<Item> findByDescriptionContainingIgnoreCase(String text);
    Optional<Item> getById(long id);
    Item save(Item item);
    void deleteById(long id);
    boolean existsById(long id);
}
