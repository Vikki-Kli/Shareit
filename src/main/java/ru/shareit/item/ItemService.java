package ru.shareit.item;

import org.springframework.stereotype.Service;
import ru.shareit.exception.AccessException;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;

import java.util.Collection;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    public ItemService(ItemRepositoryInMemory itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Collection<ItemDto> findAll(long userId) {
        User user = userRepository.getUser(userId);
        return itemRepository.findAll(user).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto getItem(long id) {
        return ItemMapper.pojoToDto(itemRepository.getItem(id));
    }

    public ItemDto createItem(ItemDto itemDto, long userId) {
        Item item = ItemMapper.dtoToPojo(itemDto);
        User owner = userRepository.getUser(userId);
        item.setOwner(owner);
        return ItemMapper.pojoToDto(itemRepository.createItem(item));
    }

    public ItemDto editItem(ItemDto itemDto, long id, long userId) {
        User owner = userRepository.getUser(userId);
        Item oldItem = itemRepository.getItem(id);
        if (!oldItem.getOwner().equals(owner)) throw new AccessException("Редактировать ресурс может только его автор");
        Item item = ItemMapper.dtoToPojo(itemDto);
        item.setOwner(owner);
        item.setId(id);
        return ItemMapper.pojoToDto(itemRepository.editItem(item, id));
    }

    public void deleteItem(long id, long userId) {
        User owner = userRepository.getUser(userId);
        Item oldItem = itemRepository.getItem(id);
        if (!oldItem.getOwner().equals(owner)) throw new AccessException("Удалить ресурс может только его автор");
        itemRepository.deleteItem(id);
    }
}
