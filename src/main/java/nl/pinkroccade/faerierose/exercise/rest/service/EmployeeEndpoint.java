package nl.pinkroccade.faerierose.exercise.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.pinkroccade.faerierose.exercise.domain.Employee;
import nl.pinkroccade.faerierose.exercise.persistence.EmployeeService;

@Path("employee")
@Component
public class EmployeeEndpoint {

    @Autowired
    private EmployeeService employeeService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteListEmployees() {
        Iterable<Employee> employees = this.employeeService.findAll();
        if (employees != null) {
            return Response.ok(employees).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
    
}
