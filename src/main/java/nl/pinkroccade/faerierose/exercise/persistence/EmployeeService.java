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
    private Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }
    
    /**
     * Create a new Employee in the database using an Employee
     * @param newEmployee the new Employee to be added to the database
     * @return the id of the new Employee or -1 if no new Employee was created
     */
    public long createNewEmployee(Employee newEmployee) {
        System.out.println("==== Employee Service 'createNewEmployee' started for Employee " + newEmployee.toString());
        // Check is newEmployee is null. If it is, return -1
        if (newEmployee != null) {
            System.out.println("==== Employee Service 'createNewEmployee' started for id " + newEmployee.getId());
            // Remove an id if it is already present
            if (newEmployee.getId() > 0) newEmployee.setId(0);
            if (this.checkName(newEmployee.getName())) {
                return this.save(newEmployee).getId(); 
            }
        }
        return -1;
    }

    /**
     * Create a new Employee in the database using a name as input
     * @param name the name of the new Employee
     * @return the id of the new Employee or -1 if no new Employee was created
     */
    public long createNewEmployeeByName(String name) {
        System.out.println("==== Employee Service 'createNewEmployeeByName' started for name " + name);
        Employee newEmployee = new Employee();
        newEmployee.setName(name);
        return this.createNewEmployee(newEmployee);
    }

    /**
     * Checks if the name is not null, unique and longer than 2 characters
     * @param name the name to be checked
     * @return true if all checks passed, otherwise false
     */
    private boolean checkName(String name) {
        System.out.println("==== Employee Service 'checkName' started");
        if (name == null) return false;
        if (name.length() < 3) return false;
        
        Iterable<Employee> employees = this.employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
    
    public void removeEmployee(long id) {
        this.employeeRepository.delete(id);
    }
}
