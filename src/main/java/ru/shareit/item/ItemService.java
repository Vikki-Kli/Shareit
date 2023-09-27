package ru.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shareit.exception.AccessException;
import ru.shareit.exception.NoSuchItemException;
import ru.shareit.user.User;
import ru.shareit.user.UserRepository;
import ru.shareit.user.UserService;

import java.util.Collection;

@Service
@Slf4j
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ItemService(@Qualifier("itemRepositoryJPA") ItemRepository itemRepository,
                       @Qualifier("userRepositoryJPA") UserRepository userRepository,
                       UserService userService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Collection<ItemDto> findAll(long userId) {
        User user = checkAndReturnUser(userId);
        return itemRepository.findAllByOwner(user).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto getItem(long id) {
        checkItemById(id);
        return ItemMapper.pojoToDto(itemRepository.findById(id).get());
    }

    public Collection<ItemDto> search(String text) {
        return itemRepository.findByDescriptionContainingIgnoreCase(text).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto createItem(ItemDto itemDto, long userId) {
        Item item = ItemMapper.dtoToPojo(itemDto);
        User owner = checkAndReturnUser(userId);
        item.setOwner(owner);

        Item savedItem = itemRepository.save(item);
        log.info("Создано " + savedItem);
        return ItemMapper.pojoToDto(savedItem);
    }

    public ItemDto editItem(ItemDto itemDto, long id, long userId) {
        User owner = checkAndReturnUser(userId);
        Item item = checkAndReturnItem(id);
        if (!item.getOwner().equals(owner)) throw new AccessException("Редактировать ресурс может только его автор");

        item = ItemMapper.dtoToPojo(itemDto);
        item.setOwner(owner);
        item.setId(id);

        Item savedItem = itemRepository.save(item);
        log.info("Изменено " + savedItem);
        return ItemMapper.pojoToDto(savedItem);
    }

    public void deleteItem(long id, long userId) {
        User owner = checkAndReturnUser(userId);
        Item item = checkAndReturnItem(id);
        if (!item.getOwner().equals(owner)) throw new AccessException("Удалить ресурс может только его автор");

        itemRepository.deleteById(id);
        log.info("Удалена вещь " + id);
    }

    public void checkItemById(long id) {
        if (!itemRepository.existsById(id)) {
            throw new NoSuchItemException("Не найдена вещь " + id);
        }
    }

    private Item checkAndReturnItem(long id) {
        checkItemById(id);
        return itemRepository.findById(id).get();
    }

    private User checkAndReturnUser(long id) {
        userService.checkUserById(id);
        return userRepository.findById(id).get();
    }
}
