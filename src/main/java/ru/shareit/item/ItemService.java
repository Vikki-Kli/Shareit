package ru.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shareit.exception.AccessException;
import ru.shareit.exception.NoSuchItemException;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;
import ru.shareit.user.UserService;

import java.util.Collection;

@Service
@Slf4j
public class ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private UserService userService;

    public ItemService(@Qualifier("itemRepositoryJPA") ItemRepository itemRepository,
                       @Qualifier("userRepositoryJPA") UserRepository userRepository,
                       UserService userService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Collection<ItemDto> findAll(long userId) {
        userService.checkUserById(userId);
        User user = userRepository.getById(userId).get();
        return itemRepository.findAllByOwner(user).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto getItem(long id) {
        checkItemById(id);
        return ItemMapper.pojoToDto(itemRepository.getById(id).get());
    }

    public Collection<ItemDto> search(String text) {
        return itemRepository.findByDescriptionContainingIgnoreCase(text).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto createItem(ItemDto itemDto, long userId) {
        Item item = ItemMapper.dtoToPojo(itemDto);
        userService.checkUserById(userId);
        User owner = userRepository.getById(userId).get();
        item.setOwner(owner);

        Item savedItem = itemRepository.save(item);
        log.info("Создано " + savedItem);
        return ItemMapper.pojoToDto(savedItem);
    }

    public ItemDto editItem(ItemDto itemDto, long id, long userId) {
        checkItemById(id);
        userService.checkUserById(userId);

        User owner = userRepository.getById(userId).get();
        Item item = itemRepository.getById(id).get();
        if (!item.getOwner().equals(owner)) throw new AccessException("Редактировать ресурс может только его автор");

        item = ItemMapper.dtoToPojo(itemDto);
        item.setOwner(owner);
        item.setId(id);

        Item savedItem = itemRepository.save(item);
        log.info("Изменено " + savedItem);
        return ItemMapper.pojoToDto(savedItem);
    }

    public void deleteItem(long id, long userId) {
        checkItemById(id);
        userService.checkUserById(userId);

        User owner = userRepository.getById(userId).get();
        Item item = itemRepository.getById(id).get();
        if (!item.getOwner().equals(owner)) throw new AccessException("Удалить ресурс может только его автор");

        itemRepository.deleteById(id);
        log.info("Удалена вещь " + id);
    }

    public void checkItemById(long id) {
        if (!itemRepository.existsById(id)) {
            throw new NoSuchItemException("Не найдена вещь " + id);
        }
    }
}
