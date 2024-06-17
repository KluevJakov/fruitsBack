package ru.jafix.fruties.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jafix.fruties.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);
}