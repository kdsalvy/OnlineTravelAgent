package online.travel.agent.view.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("City")
public class City {
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("Hotel")
	private List<Hotel> hotels;

	public City() {
	}

	public City(String name, String country, List<Hotel> hotels) {
		this.name = name;
		this.country = country;
		this.hotels = hotels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}
}
