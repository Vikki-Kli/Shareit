package ru.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.shareit.exception.NoSuchItemException;
import ru.shareit.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ItemRepositoryInMemory implements ItemRepository {

    Map<Long, Item> items = new HashMap<>();
    private static long countId = 0;

    @Override
    public Collection<Item> findAll(User user) {
        return items.values().stream().filter(s -> s.getOwner().equals(user)).collect(Collectors.toList());
    }

    @Override
    public Collection<Item> search(String text) {
        return items.values()
                .stream()
                .filter(s -> s.getAvailable() && (s.getName().contains(text) || s.getDescription().contains(text)))
                .collect(Collectors.toList());
    }

    @Override
    public Item getItem(long id) {
        if (!items.containsKey(id)) throw new NoSuchItemException("Вещь " + id + " не существует");
        return items.get(id);
    }

    @Override
    public Item createItem(Item item) {
        countId++;
        item.setId(countId);
        items.put(countId, item);
        log.info("{} has been created", item);
        return getItem(countId);
    }

    @Override
    public Item editItem(Item item, long id) {
        if (!items.containsKey(id)) throw new NoSuchItemException("Вещь " + id + " не существует");
        items.put(id, item);
        log.info("{} has been edited", item);
        return getItem(id);
    }

    @Override
    public void deleteItem(long id) {
        if (!items.containsKey(id)) throw new NoSuchItemException("Вещь " + id + " не существует");
        items.remove(id);
    }
}
