package ru.darlin.back.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.darlin.back.entity.Department;
import ru.darlin.back.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Log
public class DepartmentController {
    private final DepartmentService service;

    @GetMapping
    public List<Department> getAllDepartments() {
        return service.getAllDepartments();
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(service.createDepartment(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        try {
            return ResponseEntity.ok(service.updateDepartment(id, department));
        } catch (EntityNotFoundException e) {
            log.warning(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            service.deleteDepartment(id);
        } catch (EntityNotFoundException e) {
            log.warning(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}