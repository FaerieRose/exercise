package nl.pinkroccade.faerierose.exercise.persistence;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.pinkroccade.faerierose.exercise.domain.Employee;
import nl.pinkroccade.faerierose.exercise.domain.model.EmployeeModelBasic;


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
        System.out.print("==== Employee Service 'checkName' started");
        if (name == null) return false;
        if (name.length() < 3) return false;
        System.out.print(" name = " + name + "\n");
        
        Iterable<Employee> employees = this.employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Update an existing Employee
     * @param employee the Employee to be updated
     * @return true if the update was executed, otherwise false
     */
    public boolean updateEmployee(Employee employee) {
        System.out.println("==== Employee Service 'updateEmployee' started");
        if (employee != null && employee.getId() > 0 && checkName(employee.getName())) {
            this.save(employee);
            return true;
        }
        return false;
    }
    
    /**
     * Remove an existing Employee if she exists
     * @param id the id of the Employee to be removed
     * @return true if the Employee existed and was removed, otherwise false
     */
    public boolean removeEmployee(long id) {
        Employee employee = this.findById(id);
        if (employee != null) {
            this.removePartner(employee);
            this.employeeRepository.delete(id);
            return true;
        }
        return false;
    }
    
    /**
     * Add a partner to an Employee. The relation is always two way so the Employee is added as a partner to the partner as well.
     * @param employee the Employee to which the partner has to be added
     * @param partner the partner that has to be added to the Employee
     * @return 1 if the relation was created or already exists<br> 
     * -1 if employee is null or does not exist,<br>
     * -2 if partner is null or does not exist, <br>
     * -3 if employee already has a partner that is not the partner to be added, <br>
     * -4 if partner if already has a partner that is not the employee 
     */
    public int addPartner(Employee employee, Employee partner) {
        if (employee == null || this.findById(employee.getId()) == null) return -1;
        if (partner  == null || this.findById(partner.getId())  == null) return -2;
        if (employee.retrievePartner() != null && !employee.retrievePartner().equals(partner))  return -3;
        if (partner.retrievePartner()  != null && !partner.retrievePartner().equals(employee)) return -4;
        employee.setPartner(partner);
        partner.setPartner(employee);
        this.save(employee);
        this.save(partner);
        return 1;
    }
    
    /**
     * Add a partner to an Employee based on id's only. The relation is always two way so the Employee is added as a partner to the partner as well.
     * @param idEmployee the id of the Employee to which the partner has to be added
     * @param idPartner the id of the partner that has to be added to the Employee
     * @return see addPartner(Employee employee, Employee partner)
     */
    public int addPartner(long idEmployee, long idPartner) {
        return addPartner(this.findById(idEmployee), this.findById(idPartner));
    }
    
    /**
     * Remove a partner. As the relation must always be two way, the Employee is removed as partner from the partner as well.
     * @param employee the Employee where the partner has to be removed from
     * @return true if Employee exists and relations were removed, otherwise false
     */
    public boolean removePartner(Employee employee) {
        if (employee == null || this.findById(employee.getId()) == null) return false;
        Employee employeeDB = this.findById(employee.getId());
        Employee partner = employeeDB.retrievePartner();
        if (partner != null) {
            partner.setPartner(null);
            this.save(partner);
            employeeDB.setPartner(null);
            this.save(employeeDB);
        }
        return true;
    }
    
    public List<EmployeeModelBasic> possiblePartnerList(Employee employee) {
        List<EmployeeModelBasic> partnerList = new ArrayList<>();
        if (employee != null && this.findById(employee.getId()) != null || employee.retrievePartner() == null) {
            Iterable<Employee> employees = this.findAll();
            for (Employee employeeDB : employees) {
                if (employeeDB.retrievePartner() == null && !employeeDB.equals(employee)) {
                    partnerList.add(new EmployeeModelBasic(employeeDB));
                }
            }
        }
        return partnerList;
    }
}
