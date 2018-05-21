package online.travel.agent.view.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Amenity")
public class Amenity {
	@JsonProperty("Name")
	private String name;

	public Amenity() {
	}

	public Amenity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
