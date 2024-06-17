package ru.jafix.fruties.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.jafix.fruties.entities.dto.Delivery;
import ru.jafix.fruties.entities.dto.Payment;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
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
    protected List<Bouquet> bouquets;
    protected Boolean approved;
}
