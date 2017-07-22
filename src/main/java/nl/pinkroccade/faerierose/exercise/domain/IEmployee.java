package nl.pinkroccade.faerierose.exercise.domain;

public interface IEmployee extends IEmployeeName {
	void setId(long id);
	
	void setName(String name);

	long getPartnerId();
	void setPartner(Employee employee);
	Employee retrievePartner();
}
