package online.travel.agent.view.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("City")
public class City {
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Country")
	private Country country;
	@JsonProperty("Hotels")
	private List<Hotel> hotels;

	public City() {
	}

	public City(String name, Country country, List<Hotel> hotels) {
		this.name = name;
		this.country = country;
		this.hotels = hotels;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
