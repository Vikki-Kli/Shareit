package ru.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping()
    public Collection<ItemDto> findAll(@RequestHeader("X-ShareIt-User-Id") long userId) {
        return itemService.findAll(userId);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable long id) {
        return itemService.getItem(id);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam("text") String text) {
        return itemService.search(text);
    }

    @PostMapping()
    public ItemDto createItem(@Valid @RequestBody ItemDto item, @RequestHeader("X-ShareIt-User-Id") long userId) {
        return itemService.createItem(item, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto editItem(@Valid @RequestBody ItemDto item,
                            @PathVariable long id,
                            @RequestHeader("X-ShareIt-User-Id") long userId) {
        return itemService.editItem(item, id, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable long id, @RequestHeader("X-ShareIt-User-Id") long userId) {
        itemService.deleteItem(id, userId);
        return "Item " + id + " has been deleted";
    }
}
