package nl.pinkroccade.faerierose.exercise.domain;

public interface IEmployee extends IEmployeeName {
	void setId(long id);
	
	void setName(String name);

	IEmployeeName getPartner();
	void setPartner(Employee employee);
	Employee retrievePartner();
}
