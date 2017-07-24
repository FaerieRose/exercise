package nl.pinkroccade.faerierose.exercise.rest.service;

import java.util.ArrayList;
import java.util.List;

public class RestContainer {
	private Object load;
	private List<Resource> resources = new ArrayList<>();
	
	public RestContainer(Object load) {
		this.setLoad(load);
	}

	public Object getLoad() {
		return load;
	}
	public void setLoad(Object load) {
		this.load = load;
	}

	public List<Resource> getResources() {
		return resources;
	}
	public void addResource(Resource resource) {
		this.resources.add(resource);
	}
}
