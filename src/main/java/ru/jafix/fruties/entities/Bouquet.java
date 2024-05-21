package ru.jafix.fruties.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Bouquet {
    @Id
    protected UUID uuid;
    protected Integer id;
    protected String name;
    protected String img;
    protected String imageUuid;
    protected Integer quantity;
    @ManyToMany
    protected List<Ingredient> composition;
    protected String description;
    protected String section;
    protected String price;
}
