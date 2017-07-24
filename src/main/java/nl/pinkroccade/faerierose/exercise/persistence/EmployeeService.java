package nl.pinkroccade.faerierose.exercise.persistence;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.pinkroccade.faerierose.exercise.domain.Employee;
import nl.pinkroccade.faerierose.exercise.domain.EmployeeModelName;
import nl.pinkroccade.faerierose.exercise.domain.IEmployee;
import nl.pinkroccade.faerierose.exercise.domain.IEmployeeName;


@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    /**
     * Retrieving all Employees in the database
     * @return a list with all Employees
     */
    public List<Employee> getAllEmployeesInDatabase() {
        System.out.println(getTime() + " ==== Employee Service 'findAll' started");
//        Iterable<Employee> result = this.employeeRepository.findAll();
        List<Employee> result = (List<Employee>) this.employeeRepository.findAll();
        return result;
    }
    
    /**
     * Retrieve one Employee from the database with a specific id
     * @param id the id of the Employee
     * @return the Employee with the id or null of she does not exist
     */
    public IEmployee findEmployeeInDatabase(Long id) {
        System.out.println(getTime() + " ==== Employee Service 'findEmployeeInDatabase' started with id " + id);
        Employee result = this.employeeRepository.findOne(id);
        return result;
    }
    
    /**
     * Retrieve one Employee from the database with a specific name
     * @param name the name of the Employee
     * @return the Employee with the name or null of she does not exist
     */
    public IEmployee findEmployeeInDatabase(String name) {
        System.out.println(getTime() + " ==== Employee Service 'findEmployeeInDatabase' started with name " + name);
        Iterable<Employee> employees = this.employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        return null;
    }
    
    /**
     * Create a new Employee in the database using an Employee
     * @param newEmployee the new Employee to be added to the database
     * @return the id of the new Employee or -1 if no new Employee was created
     */
    public long createNewEmployee(Employee newEmployee) {
        System.out.println(getTime() + " ==== Employee Service 'createNewEmployee' started");
        // Check is newEmployee is null. If it is, return -1
        if (newEmployee != null) {
            // Remove an id if it is already present
            if (newEmployee.getId() > 0) newEmployee.setId(0);
            if (this.checkName(newEmployee.getName())) {
                return this.saveEmployeeInDatabase(newEmployee).getId(); 
            }
        }
        return -1;
    }

    /**
     * Create a new Employee in the database using a name as input
     * @param name the name of the new Employee
     * @return the id of the new Employee or -1 if no new Employee was created
     */
    public long createNewEmployee(String name) {
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
        System.out.println(getTime() + " ==== Employee Service 'checkName' started");
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

    /**
     * Saving an Employee in the database
     * @param employee the Employee to be saved
     */
    private IEmployee saveEmployeeInDatabase(Employee employee) {
        System.out.println(getTime() + " ==== Employee Service 'saveEmployeeInDatabase' started for id " + employee.getId());
        return this.employeeRepository.save(employee);
    }
    
    /**
     * Update an existing Employee
     * @param employee the Employee to be updated
     * @return true if the update was executed, otherwise false
     */
    public boolean updateEmployee(Employee employee) {
        System.out.println(getTime() + " ==== Employee Service 'updateEmployee' started");
        if (employee != null && this.findEmployeeInDatabase(employee.getId()) != null && checkName(employee.getName())) {
        	employee.setPartner(this.findEmployeeInDatabase(employee.getId()).retrievePartner());
            this.saveEmployeeInDatabase(employee);
            return true;
        }
        return false;
    }
    
    /**
     * Remove an existing Employee if she exists
     * @param id the id of the Employee to be removed
     * @return true if the Employee existed and was removed, otherwise false
     */
    public boolean deleteEmployeeFromDatabase(long id) {
        System.out.println(getTime() + " ==== Employee Service 'removeEmployee' started");
        IEmployee employee = this.findEmployeeInDatabase(id);
        if (employee != null) {
            this.removePartnerFromEmployee(employee);
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
    public int addPartnerToEmployee(IEmployee employee, IEmployee partner) {
        System.out.println(getTime() + " ==== Employee Service 'addPartnerToEmployee' started");
        if (employee == null || this.findEmployeeInDatabase(employee.getId()) == null) return -1;
        if (partner  == null || this.findEmployeeInDatabase(partner.getId())  == null) return -2;
        if (employee.retrievePartner() != null && !employee.retrievePartner().equals(partner)) return -3;
        if (partner.retrievePartner()  != null && !partner.retrievePartner().equals(employee)) return -4;
        if (employee instanceof Employee && partner instanceof Employee) {
            employee.setPartner((Employee) partner);
            partner.setPartner((Employee) employee);
            this.saveEmployeeInDatabase((Employee) employee);
            this.saveEmployeeInDatabase((Employee) partner);
            return 1;
        }
        return 0;
    }
    
    /**
     * Add a partner to an Employee based on id's only. The relation is always two way so the Employee is added as a partner to the partner as well.
     * @param idEmployee the id of the Employee to which the partner has to be added
     * @param idPartner the id of the partner that has to be added to the Employee
     * @return see addPartner(Employee employee, Employee partner)
     */
    public int addPartnerToEmployee(long idEmployee, long idPartner) {
        return addPartnerToEmployee(this.findEmployeeInDatabase(idEmployee), this.findEmployeeInDatabase(idPartner));
    }
    
    /**
     * Remove a partner. As the relation must always be two way, the Employee is removed as partner from the partner as well.
     * @param employee the Employee where the partner has to be removed from
     * @return true if Employee exists and relations were removed, otherwise false
     */
    public boolean removePartnerFromEmployee(IEmployee employee) {
        System.out.println(getTime() + " ==== Employee Service 'removePartnerFromEmployee' started");
        if (employee == null || this.findEmployeeInDatabase(employee.getId()) == null) return false;
        IEmployee employeeDB = this.findEmployeeInDatabase(employee.getId());
        IEmployee partner = (IEmployee) employeeDB.retrievePartner();
        if (partner != null) {
            if (employee instanceof Employee && partner instanceof Employee) {
	            partner.setPartner(null);
	            employeeDB.setPartner(null);
	            this.saveEmployeeInDatabase((Employee) partner);
	            this.saveEmployeeInDatabase((Employee) employeeDB);
            }
        }
        return true;
    }
    
    /**
     * 
     * @param employee
     * @return
     */
    public List<IEmployeeName> findPossiblePartners(IEmployee employee) {
        System.out.println(getTime() + " ==== Employee Service 'findPossiblePartners' started");
        List<IEmployeeName> partnerList = new ArrayList<>();
        if (employee != null && this.findEmployeeInDatabase(employee.getId()) != null && employee.getPartner() == null) {
            Iterable<Employee> employees = this.getAllEmployeesInDatabase();
            for (IEmployee employeeDB : employees) {
                if (employeeDB.getPartner() == null && !employeeDB.equals(employee)) {
                    partnerList.add(new EmployeeModelName(employeeDB));
                }
            }
        }
        return partnerList;
    }
    
    private String getTime() {
    	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd '-' HH:mm:ss.SSS");
    	return ft.format(new Date());
    }
}
