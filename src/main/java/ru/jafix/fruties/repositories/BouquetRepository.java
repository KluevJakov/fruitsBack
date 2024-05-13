package ru.jafix.fruties.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jafix.fruties.entities.Bouquet;
import ru.jafix.fruties.entities.Ingredient;

import java.util.List;
import java.util.UUID;

@Repository
public interface BouquetRepository extends JpaRepository<Bouquet, UUID> {

}
