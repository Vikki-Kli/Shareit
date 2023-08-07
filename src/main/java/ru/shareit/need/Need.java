package ru.shareit.need;

import lombok.Builder;
import lombok.Data;
import ru.shareit.item.Item;
import ru.shareit.user.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Need {

    private Long id;
    private User user;
    private String description;
    private List<Item> offers = new ArrayList<>();

}
