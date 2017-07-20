package nl.pinkroccade.faerierose.exercise.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique=true, nullable=false, length=32)
    private String name;

    
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
    
    
}
