package nl.pinkroccade.faerierose.exercise.domain;

public class Resource {
	private String link;         // Must start with http
	private String type;         // GET, POST, PUT, DELETE
	private String description;  // Max 64 characters
	
	public static final String GET    = "GET";
	public static final String PUT    = "PUT";
	public static final String POST   = "POST";
	public static final String DELETE = "DELETE";
	public static String startUrl = "http://localhost:8081/api";
	
	public Resource(String type, String link, String description) {
		this.setLink(link);
		this.setType(type);
		this.setDescription(description);
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		if (link.substring(0, 4).toLowerCase().equals("http")) {
			this.link = link;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		String t = type.toUpperCase();
		if (t.equals(GET) || t.equals(PUT) || t.equals(POST) || t.equals(DELETE)) {
			this.type = type;
		}
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if (description.length() <= 64) {
			this.description = description;
		}
	}
	
	
}
