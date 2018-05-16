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

public class DBOtoDTOConverter {

	public static City convert(CityDBO dbo) {
		String name = dbo.getName();
		String country = dbo.getCountry().getName();
		List<HotelDBO> hotelDbos = dbo.getHotels();
		List<Hotel> hotels = new ArrayList<>();
		for (HotelDBO hotelDbo : hotelDbos) {
			Hotel hotel = convert(hotelDbo);
			if (hotel.isActive())
				hotels.add(hotel);
		}
		return new City(name, country, hotels);
	}

	public static Hotel convert(HotelDBO dbo) {
		List<AmenityDBO> amenityDbos = dbo.getAmenities();
		List<Amenity> amenities = null;
		if (amenityDbos != null) {
			amenities = new ArrayList<>();
			for (AmenityDBO amenityDbo : amenityDbos) {
				Amenity amenity = convert(amenityDbo);
				amenities.add(amenity);
			}
		}
		return new Hotel(dbo.getName(), dbo.getDescription(), dbo.getAddress(), dbo.getContact(),
				dbo.getCity().getName(), amenities, dbo.isActive());
	}

	public static Country convert(CountryDBO dbo) {
		String name = dbo.getName();
		List<CityDBO> cityDBOList = dbo.getCities();
		List<City> cityList = new ArrayList<>();
		for (CityDBO cityDbo : cityDBOList) {
			City city = convert(cityDbo);
			cityList.add(city);
		}
		return new Country(dbo.getId(), name, cityList);
	}

	public static Amenity convert(AmenityDBO dbo) {
		return new Amenity(dbo.getName());
	}
}
