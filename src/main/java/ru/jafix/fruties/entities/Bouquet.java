package ru.jafix.fruties.entities;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    protected UUID id;
    protected String name;
    protected String img;
    protected String imageUuid;
    protected Integer quantity;
    @ManyToMany
    protected List<Ingredient> composition;
    protected String description;
    protected String section;
    protected String price;
    protected Boolean isDefault;
    protected Boolean isNew;
    @ManyToOne
    @JoinColumn(name = "category_id")
    protected Category category;
}
