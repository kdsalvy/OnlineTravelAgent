package online.travel.agent.view.converter;

import java.util.ArrayList;
import java.util.List;

import online.travel.agent.model.dbo.CityDBO;
import online.travel.agent.model.dbo.CountryDBO;
import online.travel.agent.model.dbo.HotelDBO;
import online.travel.agent.view.dto.City;
import online.travel.agent.view.dto.Country;
import online.travel.agent.view.dto.Hotel;

public class DBOtoDTOConverter {

	public static City convert(CityDBO dbo) {
		String name = dbo.getName();
		CountryDBO countryDbo = dbo.getCountry();
		Country country = convert(countryDbo);
		List<HotelDBO> hotelDbos = dbo.getHotels();
		List<Hotel> hotels = new ArrayList<>();
		for (HotelDBO hotelDbo : hotelDbos) {
			Hotel hotel = convert(hotelDbo);
			hotels.add(hotel);
		}
		return new City(name, country, hotels);
	}

	public static Hotel convert(HotelDBO dbo) {
		City city = convert(dbo.getCity());
		return new Hotel(dbo.getName(), dbo.getDescription(), dbo.getAddress(), dbo.getContact(), city,
				dbo.getAmenities());
	}

	public static Country convert(CountryDBO dbo) {
		String name = dbo.getName();
		List<CityDBO> cityDbos = dbo.getCities();
		List<City> cities = new ArrayList<>();
		for (CityDBO cityDbo : cityDbos) {
			City city = convert(cityDbo);
			cities.add(city);
		}
		return new Country(name, cities);
	}
}
