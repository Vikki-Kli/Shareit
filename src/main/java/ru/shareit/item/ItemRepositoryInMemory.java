package ru.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.shareit.exception.NoSuchItemException;
import ru.shareit.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Lazy
@Slf4j
public class ItemRepositoryInMemory implements ItemRepository {

    Map<Long, Item> items = new HashMap<>();
    private static long countId = 0;

    @Override
    public Collection<Item> findAllByOwner(User user) {
        return items.values().stream().filter(s -> s.getOwner().equals(user)).collect(Collectors.toList());
    }

    @Override
    public Collection<Item> findByDescriptionContainingIgnoreCase(String text) {
        return items.values()
                .stream()
                .filter(s -> s.getAvailable() && (s.getName().contains(text) || s.getDescription().contains(text)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> getById(long id) {
        if (!items.containsKey(id)) throw new NoSuchItemException("Вещь " + id + " не существует");
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            countId++;
            item.setId(countId);
            log.info("{} has been created", item);
        }
        items.put(item.getId(), item);
        log.info("{} has been edited", item);
        return getById(item.getId()).get();
    }

    @Override
    public void deleteById(long id) {
        if (!items.containsKey(id)) throw new NoSuchItemException("Вещь " + id + " не существует");
        items.remove(id);
    }

    @Override
    public boolean existsById(long id) {
        return items.containsKey(id);
    }
}
