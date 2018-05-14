package online.travel.agent.view.converter;

import java.util.ArrayList;
import java.util.List;

import online.travel.agent.model.dbo.CityDBO;
import online.travel.agent.model.dbo.CountryDBO;
import online.travel.agent.model.dbo.HotelDBO;
import online.travel.agent.view.dto.City;
import online.travel.agent.view.dto.Country;
import online.travel.agent.view.dto.Hotel;

public class DTOtoDBOConverter {
	public static CityDBO convert(City dto) {
		String name = dto.getName();
		Country country = dto.getCountry();
		CountryDBO countryDbo = convert(country);
		List<Hotel> hotels = dto.getHotels();
		List<HotelDBO> hotelDbos = new ArrayList<>();
		for (Hotel hotel : hotels) {
			HotelDBO hotelDbo = convert(hotel);
			hotelDbos.add(hotelDbo);
		}
		return new CityDBO(name, countryDbo, hotelDbos);
	}

	public static HotelDBO convert(Hotel dto) {
		CityDBO cityDbo = convert(dto.getCity());
		return new HotelDBO(dto.getName(), dto.getDescription(), dto.getAddress(), dto.getContact(), cityDbo,
				dto.getAmenities());
	}

	public static CountryDBO convert(Country dto) {
		String name = dto.getName();
		List<City> cities = dto.getCities();
		List<CityDBO> cityDbos = new ArrayList<>();
		for (City city : cities) {
			CityDBO cityDbo = convert(city);
			cityDbos.add(cityDbo);
		}
		return new CountryDBO(name, cityDbos);
	}
}
