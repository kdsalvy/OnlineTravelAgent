package online.travel.agent.view.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Hotel")
public class Hotel {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Contact")
    private String contact;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Amenities")
    private List<Amenity> amenities;
    @JsonProperty("IsActive")
    private boolean isActive;

    public Hotel() {
    }

    public Hotel(String name, String description, String address, String contact, String city, List<Amenity> amenities,
	    boolean isActive) {
	this.name = name;
	this.description = description;
	this.address = address;
	this.contact = contact;
	this.city = city;
	this.amenities = amenities;
	this.isActive = isActive;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getContact() {
	return contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public List<Amenity> getAmenities() {
	return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
	this.amenities = amenities;
    }

    public boolean isActive() {
	return isActive;
    }

    public void setActive(boolean isActive) {
	this.isActive = isActive;
    }
}
