package nl.pinkroccade.faerierose.exercise.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import nl.pinkroccade.faerierose.exercise.domain.model.EmployeeModelBasic;

@Entity
public class Employee implements IEmployee{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique=true, nullable=false, length=32)
    private String name;

    @OneToOne()
    private Employee partner;
    
    /* =================================================================== */
    /* Getters & Setters                                                   */ 
    /* =================================================================== */    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    /* =================================================================== */    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /* =================================================================== */    
    /**
     * Returns the id of the Partner to prevent a loop in the code
     * @return id of partner if partner is not null, otherwise -1;
     */
    public IEmployeeView getPartner() {
        if (this.partner == null) {
            return null;
        }
        return (IEmployeeView) this.partner;
    }
    public void setPartner(Employee partner) {
        this.partner = partner;
    }
    
    /* =================================================================== */
    /* Override hashCode and equals                                        */ 
    /* =================================================================== */    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    
    
}
