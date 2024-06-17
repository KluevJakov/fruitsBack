package ru.jafix.fruties.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jafix.fruties.entities.Role;


import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}