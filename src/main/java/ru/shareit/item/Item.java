package ru.shareit.item;

import jakarta.persistence.*;
import lombok.*;
import ru.shareit.need.Need;
import ru.shareit.user.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    private String name;
    private String description;
    private Boolean available;
    @ManyToMany
    @JoinTable(name = "items_needs",
            joinColumns = @JoinColumn (name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn (name = "need_id", referencedColumnName = "id"))
    private Set<Need> offersForNeeds = new HashSet<>();

}
