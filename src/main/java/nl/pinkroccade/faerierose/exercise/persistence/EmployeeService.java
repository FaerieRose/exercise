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
        System.out.println("==== Employee Service 'findAll' started");
        Iterable<Employee> result = this.employeeRepository.findAll();
        return result;
    }
    
    /**
     * Retrieve one Employee from the database with a specific id
     * @param id the id of the Employee
     * @return the Employee with the id or null of she does not exist
     */
    public Employee findById(Long id) {
        System.out.println("==== Employee Service 'findById' started with id " + id);
        Employee result = this.employeeRepository.findOne(id);
        String s = result != null ? result.getName() : "";
        System.out.println("==== Employee Service 'findById' result " + s);
        return result;
    }
    
    /**
     * Retrieve one Employee from the database with a specific name
     * @param name the name of the Employee
     * @return the Employee with the name or null of she does not exist
     */
    public Employee findByName(String name) {
        System.out.println("==== Employee Service 'findByName' started with name " + name);
        Iterable<Employee> employees = this.employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                String s = employee != null ? employee.getName() : "";
                System.out.println("==== Employee Service 'findByName' result " + s);
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
