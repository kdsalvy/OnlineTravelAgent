package online.travel.agent.view.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Amenity")
public class Amenity {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Hotel")
    private String hotelName;

    public Amenity() {
    }
    
    public Amenity(String name) {
	this.name = name;
    }

    public Amenity(String name, String hotelName) {
	this.name = name;
	this.hotelName = hotelName;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getHotelName() {
	return hotelName;
    }

    public void setHotelName(String hotelName) {
	this.hotelName = hotelName;
    }
}
