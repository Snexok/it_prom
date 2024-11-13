package ru.darlin.back.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ru.darlin.back.entity.Employee;
import ru.darlin.back.entity.Profession;
import ru.darlin.back.repository.EmployeeRepository;
import ru.darlin.back.repository.ProfessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionService {
    private final ProfessionRepository repository;
    private final EmployeeRepository employeeRepository;

    public List<Profession> getAllProfessions() {
        return repository.findAll();
    }

    public Profession createProfession(Profession profession) {
        return repository.save(profession);
    }

    public Profession updateProfession(Long id, Profession profession) {
        return repository.findById(id)
                .map(existingProfession -> {
                    existingProfession.setName(profession.getName());
                    existingProfession.setNote(profession.getNote());
                    return repository.save(existingProfession);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Профессия с id %d не найден", id)));
    }

    public void deleteProfession(Long id) throws EntityNotFoundException {
        Profession profession = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Профессия с id %d не найден", id)));

        List<Employee> employees = employeeRepository.findAllByProfession(profession);
        for (Employee employee : employees) {
            employee.setProfession(null);
            employeeRepository.save(employee);
        }

        repository.deleteById(id);
    }
}
