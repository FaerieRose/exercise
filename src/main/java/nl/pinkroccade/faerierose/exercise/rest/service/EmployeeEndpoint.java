package nl.pinkroccade.faerierose.exercise.rest.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.pinkroccade.faerierose.exercise.domain.Employee;
import nl.pinkroccade.faerierose.exercise.domain.model.EmployeeModelBasic;
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
    
    /**
     * Get a list of possible partners for an Employee
     * @param id the id of the Employee
     * @return a list with Employees that can be partners
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("partners/{id}")
    public Response getPossiblePartners(@PathParam("id") Long id) {
        List<EmployeeModelBasic> result = this.employeeService.possiblePartnerList(this.employeeService.findById(id));
        return Response.ok(result).build();
    }
    
    /**
     * Create a new Employee with just a name as input
     * @param name the name of the new Employee
     * @return 200 (ok) with the id of the the Employee if one was created, otherwise 304 (not modified)
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("new/{name}")
    public Response postNewEmployeeName(@PathParam("name") String name) {
        long result = this.employeeService.createNewEmployeeByName(name);
        if (result > 0 ) {
            return Response.ok(result).build();
        }
        return Response.notModified().build();
    }

    /**
     * Create a new Employee with an Employee as input
     * @param newEmployee the new Employee to be added to the database
     * @return 200 (ok) with the id of the the Employee if one was created, otherwise 304 (not modified)
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("new")
    public Response postNewEmployee(Employee newEmployee) {
        long result = this.employeeService.createNewEmployee(newEmployee);
        if (result > 0 ) {
            return Response.ok(result).build();
        }
        return Response.notModified().build();
    }
    
    /**
     * Update an Employee if she exists
     * @param employee the updated Employee to be saved
     * @return 200 (ok) if the EMployee exists and is updated, otherwise 304 (not modified)
     */
    @PUT
    @Path("update")
    public Response updateEmployee(Employee employee) {
        if (this.employeeService.updateEmployee(employee)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
    
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("update/{idEmployee}/{idPartner}")
    public Response addPartnerToEmployeeUsingIDs(@PathParam("idEmployee") Long idEmployee, @PathParam("idPartner") Long idPartner) {
        int result = this.employeeService.addPartner(idEmployee, idPartner);
        if (result == 1) return Response.ok().build();
        return Response.notModified(Integer.toString(result)).build();
    }

    /**
     * Remove a partner from an Employee
     * @param id the id of the Employee from which partner has to be removed
     * @return 200 (ok) if the Employee exists and is updated, otherwise 304 (not modified)
     */
    @PUT
    @Path("update/remove_partner/{id}")
    public Response removePartner(@PathParam("id") Long id) {
        if (this.employeeService.removePartner(this.employeeService.findById(id))) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
    
    
    /**
     * Remove an Employee based on id
     * @param id the id of the Employee to be removed
     * @return 200 (ok) if the Employee exists and was removed, otherwise 304 (not modified)
     */
    @DELETE
    @Path("del/{id}")
    public Response removeEmployee(@PathParam("id") Long id) {
        if (this.employeeService.removeEmployee(id)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
}
