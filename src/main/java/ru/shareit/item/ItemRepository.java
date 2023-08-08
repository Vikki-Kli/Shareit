package ru.shareit.item;

import ru.shareit.user.User;

import java.util.Collection;

public interface ItemRepository {
    Collection<Item> findAll(User user);
    Collection<Item> search(String text);
    Item getItem(long id);
    Item createItem(Item item);
    Item editItem(Item item, long id);
    void deleteItem(long id);
}
