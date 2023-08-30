package ru.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<User, Long>, UserRepository {
}
