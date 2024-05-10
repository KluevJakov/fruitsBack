package ru.jafix.fruties.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Ingredient {
    @Id
    protected UUID id;
    protected String name;
    protected String translate;
    @ManyToOne
    protected Category category;
}
