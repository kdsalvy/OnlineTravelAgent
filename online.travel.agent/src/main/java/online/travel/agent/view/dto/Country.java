package online.travel.agent.view.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Country")
public class Country {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("City")
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

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public List<City> getCities() {
	return cities;
    }

    public void setCities(List<City> cities) {
	this.cities = cities;
    }
}
