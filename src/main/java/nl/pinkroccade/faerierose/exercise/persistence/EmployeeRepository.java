package nl.pinkroccade.faerierose.exercise.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.pinkroccade.faerierose.exercise.domain.Employee;

@Component
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
