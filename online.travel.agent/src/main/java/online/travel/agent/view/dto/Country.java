package online.travel.agent.view.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Country")
public class Country {
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Cities")
	private List<City> cities;

	public Country() {
	}
	
	public Country(String name, List<City> cities) {
		this.name = name;
		this.cities = cities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
