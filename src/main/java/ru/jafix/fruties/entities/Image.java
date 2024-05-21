package ru.jafix.fruties.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Image {
    @Id
    private UUID uuid;

    @Lob
    private byte[] data;

    private String mimeType;
}
