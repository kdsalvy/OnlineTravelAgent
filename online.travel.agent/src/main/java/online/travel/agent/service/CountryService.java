package online.travel.agent.service;

import java.util.List;

import online.travel.agent.view.dto.Country;

public interface CountryService {

	public long addCountry(Country country);

	public Country getCountry(long id);

	public List<Country> search(String key, String value);

	public boolean deleteCountry(long id);
}
