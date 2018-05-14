package online.travel.agent.service;

import java.util.List;

import online.travel.agent.view.dto.City;

public interface CityService {
	public long addCity(City city);

	public City getCity(long id);

	public List<City> search(String key, String value);

	public boolean deleteCity(long id);
}
