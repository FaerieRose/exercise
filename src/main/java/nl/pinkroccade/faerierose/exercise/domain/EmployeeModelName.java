package nl.pinkroccade.faerierose.exercise.domain;

public class EmployeeModelName implements IEmployeeName {
	private Employee employee;
	
	public EmployeeModelName(IEmployee employee) {
		if (employee instanceof Employee) this.employee = (Employee) employee;
	}
	
	public long getId() {
		return this.employee.getId();
	}
	
	public String getName() {
		return this.employee.getName();
	}
	
}
