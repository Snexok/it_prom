package ru.darlin.back.repository;

import ru.darlin.back.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
}
