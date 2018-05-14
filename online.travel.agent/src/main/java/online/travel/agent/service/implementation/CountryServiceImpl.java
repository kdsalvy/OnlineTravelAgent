package online.travel.agent.service.implementation;

import java.util.ArrayList;
import java.util.List;

import online.travel.agent.model.db.DatabaseManager;
import online.travel.agent.model.db.DatabaseManagerOperation;
import online.travel.agent.model.dbo.CountryDBO;
import online.travel.agent.service.CountryService;
import online.travel.agent.view.converter.DBOtoDTOConverter;
import online.travel.agent.view.converter.DTOtoDBOConverter;
import online.travel.agent.view.dto.Country;

public class CountryServiceImpl implements CountryService {

	DatabaseManager<CountryDBO> databaseManager = new DatabaseManagerOperation<>();

	@Override
	public long addCountry(Country country) {
		CountryDBO dbo = DTOtoDBOConverter.convert(country);
		dbo = databaseManager.insert(dbo);
		return dbo.getId();
	}

	@Override
	public Country getCountry(long id) {
		CountryDBO dbo = databaseManager.read(id, CountryDBO.class);
		Country country = DBOtoDTOConverter.convert(dbo);
		return country;
	}

	@Override
	public List<Country> search(String key, String value) {
		List<CountryDBO> dboList = databaseManager.search(key, value, CountryDBO.class);
		List<Country> countryList = new ArrayList<>();
		for (CountryDBO dbo : dboList) {
			Country country = DBOtoDTOConverter.convert(dbo);
			countryList.add(country);
		}
		return countryList;
	}

	@Override
	public boolean deleteCountry(long id) {
		return databaseManager.delete(id, CountryDBO.class);
	}

}
