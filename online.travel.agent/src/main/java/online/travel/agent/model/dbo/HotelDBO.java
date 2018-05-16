package online.travel.agent.model.dbo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Hotel")
public class HotelDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String address;
    private String contact;
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private CityDBO city;

    @ManyToMany
    @JoinTable(name = "hotel_amenity_map", joinColumns = @JoinColumn(name = "hotel_id"), inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private List<AmenityDBO> amenities;

    public HotelDBO() {
    }

    public HotelDBO(String name, String description, String address, String contact, CityDBO city,
	    List<AmenityDBO> amenities, boolean isActive) {
	this.name = name;
	this.description = description;
	this.address = address;
	this.contact = contact;
	this.city = city;
	this.amenities = amenities;
	this.isActive = isActive;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
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

    public CityDBO getCity() {
	return city;
    }

    public void setCity(CityDBO city) {
	this.city = city;
    }

    public List<AmenityDBO> getAmenities() {
	return amenities;
    }

    public void setAmenities(List<AmenityDBO> amenities) {
	this.amenities = amenities;
    }

    public String getContact() {
	return contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public boolean isActive() {
	return isActive;
    }

    public void setActive(boolean isActive) {
	this.isActive = isActive;
    }
}
