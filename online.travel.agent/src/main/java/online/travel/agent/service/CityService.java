package online.travel.agent.service;

import java.util.List;

import online.travel.agent.view.dto.City;
import online.travel.agent.view.dto.Hotel;

public interface CityService {
	public long addCity(City city) throws Exception;

	public City getCity(long id);

	public List<City> search(String key, String value);

	public boolean deleteCity(long id);

	public List<Hotel> getHotelForCity(String cityName) throws Exception;
}
