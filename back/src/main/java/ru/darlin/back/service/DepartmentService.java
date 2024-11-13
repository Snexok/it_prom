package ru.darlin.back.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ru.darlin.back.entity.Department;
import ru.darlin.back.entity.Employee;
import ru.darlin.back.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import ru.darlin.back.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;
    private final EmployeeRepository employeeRepository;

    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    public Department createDepartment(Department department) {
        return repository.save(department);
    }

    public Department updateDepartment(Long id, Department department) throws EntityNotFoundException {
        return repository.findById(id)
                .map(existingDepartment -> {
                    existingDepartment.setName(department.getName());
                    existingDepartment.setNote(department.getNote());
                    existingDepartment.setParent(department.getParent());
                    return repository.save(existingDepartment);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Отдел с id %d не найден", id)));
    }

    public void deleteDepartment(Long id) throws EntityNotFoundException {
        Department department = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Отдел с id %d не найден", id)));

        List<Department> childDepartments = repository.findAllByParent(department);
        for (Department child : childDepartments) {
            child.setParent(null);
            repository.save(child);
        }

        List<Employee> employees = employeeRepository.findAllByDepartment(department);
        for (Employee employee : employees) {
            employee.setDepartment(null);
            employeeRepository.save(employee);
        }

        repository.deleteById(id);
    }
}
