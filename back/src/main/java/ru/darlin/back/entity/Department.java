package ru.darlin.back.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String note;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Department parent;
}