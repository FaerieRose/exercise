package nl.pinkroccade.faerierose.exercise.domain;

public interface IEmployee extends IEmployeeView {
	void setId(long id);
	void setName(String name);
	void setPartner(Employee employee);
}
