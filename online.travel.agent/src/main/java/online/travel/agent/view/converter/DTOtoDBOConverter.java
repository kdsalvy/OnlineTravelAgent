package online.travel.agent.view.converter;

import java.util.ArrayList;
import java.util.List;

import online.travel.agent.model.dbo.AmenityDBO;
import online.travel.agent.model.dbo.CityDBO;
import online.travel.agent.model.dbo.CountryDBO;
import online.travel.agent.model.dbo.HotelDBO;
import online.travel.agent.view.dto.Amenity;
import online.travel.agent.view.dto.City;
import online.travel.agent.view.dto.Country;
import online.travel.agent.view.dto.Hotel;

public class DTOtoDBOConverter {
    public static CityDBO convert(City dto) {
	String name = dto.getName();
	return new CityDBO(name, null, null);
    }

    public static HotelDBO convert(Hotel dto) {
	List<Amenity> amenities = dto.getAmenities();
	List<AmenityDBO> amenityDbos = null;
	if (amenities != null) {
	    amenityDbos = new ArrayList<>();
	    for (Amenity amenity : amenities) {
		AmenityDBO amenityDbo = convert(amenity);
		amenityDbos.add(amenityDbo);
	    }
	}
	return new HotelDBO(dto.getName(), dto.getDescription(), dto.getAddress(), dto.getContact(), null, amenityDbos,
		dto.isActive());
    }

    public static CountryDBO convert(Country dto) {
	String name = dto.getName();
	Long id = dto.getId();
	return new CountryDBO(id, name, null);
    }

    public static AmenityDBO convert(Amenity dto) {
	return new AmenityDBO(dto.getName());
    }
}
