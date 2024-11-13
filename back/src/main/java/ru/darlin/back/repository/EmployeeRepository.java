package ru.darlin.back.repository;

import ru.darlin.back.entity.Department;
import ru.darlin.back.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.darlin.back.entity.Profession;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDepartment(Department department);
    List<Employee> findAllByProfession(Profession profession);
}
