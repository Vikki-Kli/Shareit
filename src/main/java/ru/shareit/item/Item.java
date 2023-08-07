package ru.shareit.item;

import lombok.Builder;
import lombok.Data;
import ru.shareit.user.User;

@Data
@Builder
public class Item {

    private Long id;
    private User owner;
    private String name;
    private String description;
    private Boolean available;

}
