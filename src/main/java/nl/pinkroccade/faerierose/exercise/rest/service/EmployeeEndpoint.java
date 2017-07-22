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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.pinkroccade.faerierose.exercise.domain.Employee;
import nl.pinkroccade.faerierose.exercise.domain.IEmployee;
import nl.pinkroccade.faerierose.exercise.domain.IEmployeeView;
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
    @Path("findall")
    public Response getCompleteListEmployees() {
        Iterable<Employee> result = this.employeeService.getAllEmployeesInDatabase();
        if (result != null) {
            return Response.ok(result).build();
        }
        return Response.noContent().build();
    }
    
    /**
     * Get an Employee with a specific id or name
     * @param id the id that is being searched
     * @return 200 (ok) + JSON object with Employee OR 204 (no content)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("find/{search_criteria}")
    public Response getEmployeeById(@PathParam("search_criteria") String param) {
    	IEmployee result;
    	if (StringUtils.isNumber(param)) {
            result = this.employeeService.findEmployeeInDatabase(Long.parseLong(param));
    	} else {
            result = this.employeeService.findEmployeeInDatabase(param);
    	}
        if (result != null && result instanceof Employee) {
            return Response.ok((Employee) result).build();
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
        List<IEmployeeView> result = this.employeeService.findPossiblePartners(this.employeeService.findEmployeeInDatabase(id));
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
        long result = this.employeeService.createNewEmployee(name);
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
    @Path("{id}/update")
    public Response updateEmployee(@PathParam("id") Long id, Employee employee) {
        if (this.employeeService.updateEmployee(employee)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
    

    /**
     * Remove a partner from an Employee
     * @param id the id of the Employee from which partner has to be removed
     * @return 200 (ok) if the Employee exists and is updated, otherwise 304 (not modified)
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/partner/{param}")
    public Response addPartnerToEmployeeUsingIDs(
    		@PathParam("id") Long id, 
    		@PathParam("param") String param, 
    		@QueryParam("id_partner") long idPartner) {
    	if (param.equals("remove")) {
            if (this.employeeService.removePartnerFromEmployee(this.employeeService.findEmployeeInDatabase(id))) {
                return Response.ok().build();
            }
    	} else if (param.equals("add")) {
            int result = this.employeeService.addPartnerToEmployee(id, idPartner);
            if (result == 1) return Response.ok().build();
            return Response.notModified(Integer.toString(result)).build();
    	}
        return Response.notModified().build();
    }
    
    /**
     * Remove an Employee based on id
     * @param id the id of the Employee to be removed
     * @return 200 (ok) if the Employee exists and was removed, otherwise 304 (not modified)
     */
    @DELETE
    @Path("{id}/del")
    public Response removeEmployee(@PathParam("id") Long id) {
        if (this.employeeService.deleteEmployeeFromDatabase(id)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
}
