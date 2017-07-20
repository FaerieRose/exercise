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
    
    /**
     * Retrieving all Employees in the database
     * @return a list with all Employees
     */
    public Iterable<Employee> findAll() {
        Iterable<Employee> result = this.employeeRepository.findAll();
        return result;
    }
    
    /**
     * Retrieve one Employee from the database with a specific id
     * @param id the id of the Employee
     * @return the Employee with the id or null of she does not exist
     */
    public Employee findById(long id) {
        Employee result = this.employeeRepository.findOne(id);
        return result;
    }
    
    /**
     * Retrieve one Employee from the database with a specific name
     * @param name the name of the Employee
     * @return the Employee with the name or null of she does not exist
     */
    public Employee findByName(String name) {
        Iterable<Employee> employees = this.employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        return null;
    }
    
    /**
     * Saving an Employee in the database
     * @param employee the Employee to be saved
     */
    public void save(Employee employee) {
        this.employeeRepository.save(employee);
    }

}
