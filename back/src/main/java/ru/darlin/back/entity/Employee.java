package ru.darlin.back.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String note;
    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}