package ru.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryJPA extends JpaRepository<Item, Long>, ItemRepository {
}
