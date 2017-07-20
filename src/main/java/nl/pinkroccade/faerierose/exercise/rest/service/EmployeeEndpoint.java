package nl.pinkroccade.faerierose.exercise.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
    
    /**
     * GET a list of all the Employees in the database
     * @return 200 (ok) + JSON object with all Employees OR 204 (no content)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteListEmployees() {
        Iterable<Employee> result = this.employeeService.findAll();
        if (result != null) {
            return Response.ok(result).build();
        }
        return Response.noContent().build();
    }
    
    /**
     * Get an Employee with a specific id
     * @param id the id that is being searched
     * @return 200 (ok) + JSON object with Employee OR 204 (no content)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("id/{id}")
    public Response getEmployeeById(@PathParam("id") Long id) {
        Employee result = this.employeeService.findById(id);
        if (result != null) {
            return Response.ok(result).build();
        }
        return Response.noContent().build();
    }
    
    /**
     * Get an Employee with a specific name
     * @param name the name that is being searched
     * @return 200 (ok) + JSON object with Employee OR 204 (no content)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}")
    public Response getEmployeeById(@PathParam("name") String name) {
        Employee result = this.employeeService.findByName(name);
        if (result != null) {
            return Response.ok(result).build();
        }
        return Response.noContent().build();
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("new/{name}")
    public Response postNewEmployeeName(@PathParam("name") String name) {
        long result = this.employeeService.createNewEmployeeByName(name);
        return Response.ok(result).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("new")
    public Response postNewEmployee(Employee newEmployee) {
        long result = this.employeeService.createNewEmployee(newEmployee);
        return Response.ok(result).build();
    }
    

    @DELETE
    @Path("del/{id}")
    public Response removeEmployee(@PathParam("id") Long id) {
        this.employeeService.removeEmployee(id);
        return Response.ok().build();
    }
    
}
