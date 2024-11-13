package ru.darlin.back.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.darlin.back.entity.Profession;
import ru.darlin.back.service.ProfessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professions")
@RequiredArgsConstructor
@Log
public class ProfessionController {
    private final ProfessionService service;

    @GetMapping
    public List<Profession> getAllProfessions() {
        return service.getAllProfessions();
    }

    @PostMapping
    public ResponseEntity<Profession> createProfession(@RequestBody Profession profession) {
        return ResponseEntity.ok(service.createProfession(profession));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profession> updateProfession(@PathVariable Long id, @RequestBody Profession profession) {
        try {
            return ResponseEntity.ok(service.updateProfession(id, profession));
        } catch (EntityNotFoundException e) {
            log.warning(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        try {
            service.deleteProfession(id);
        } catch (EntityNotFoundException e) {
            log.warning(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}