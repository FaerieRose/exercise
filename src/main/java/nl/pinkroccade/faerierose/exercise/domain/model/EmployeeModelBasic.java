package nl.pinkroccade.faerierose.exercise.domain.model;

import nl.pinkroccade.faerierose.exercise.domain.Employee;

public class EmployeeModelBasic {
    private Employee employee;
    
    public EmployeeModelBasic(Employee employee) {
        this.employee = employee;
    }
    
    public long getId() {
        return this.employee.getId();
    }
    
    public String getName() {
        return this.employee.getName();
    }
    
}
