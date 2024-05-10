package ru.jafix.fruties.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_table")
@ToString
public class Order {
    @Id
    protected UUID id;
    protected String customerName;
    protected String phoneCustomer;
    protected String email;
    protected String phoneReceiver;
    @Enumerated(EnumType.STRING)
    protected Delivery delivery;
    protected Date deliveryDate;
    protected String address;
    protected String comment;
    @Enumerated(EnumType.STRING)
    protected Payment paymentMethod;
    @ManyToMany
    protected List<Ingredient> ingredients;
    @ManyToMany
    protected List<Bouquet> bouquets;
}
