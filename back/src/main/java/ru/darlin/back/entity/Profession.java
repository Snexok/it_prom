package ru.darlin.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Profession {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String note;
}
