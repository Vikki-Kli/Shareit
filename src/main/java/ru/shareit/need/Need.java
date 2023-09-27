package ru.shareit.need;

import jakarta.persistence.*;
import lombok.*;
import ru.shareit.item.Item;
import ru.shareit.user.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "needs")
public class Need {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String description;
    @ManyToMany
    @JoinTable(name = "items_needs",
            joinColumns = @JoinColumn (name = "need_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn (name = "item_id", referencedColumnName = "id"))
    private Set<Item> offers = new HashSet<>();

}
