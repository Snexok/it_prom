package ru.darlin.back.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ru.darlin.back.entity.Employee;
import ru.darlin.back.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        return repository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setFullName(employee.getFullName());
                    existingEmployee.setNote(employee.getNote());
                    existingEmployee.setProfession(employee.getProfession());
                    existingEmployee.setDepartment(employee.getDepartment());
                    return repository.save(existingEmployee);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Сотрудник с id %d не найден", id)));
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
