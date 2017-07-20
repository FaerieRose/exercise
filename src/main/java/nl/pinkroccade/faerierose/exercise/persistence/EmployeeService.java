package nl.pinkroccade.faerierose.exercise.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.pinkroccade.faerierose.exercise.domain.Employee;


@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Iterable<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

}
