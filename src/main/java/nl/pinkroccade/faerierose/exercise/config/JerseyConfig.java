package nl.pinkroccade.faerierose.exercise.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import nl.pinkroccade.faerierose.exercise.rest.service.EmployeeEndpoint;


@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        register(EmployeeEndpoint.class);
    }
}