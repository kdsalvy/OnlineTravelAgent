package online.travel.agent.service.implementation;

import java.util.ArrayList;
import java.util.List;

import online.travel.agent.model.db.DatabaseManager;
import online.travel.agent.model.db.DatabaseManagerOperation;
import online.travel.agent.model.dbo.CityDBO;
import online.travel.agent.service.CityService;
import online.travel.agent.view.converter.DBOtoDTOConverter;
import online.travel.agent.view.converter.DTOtoDBOConverter;
import online.travel.agent.view.dto.City;

public class CityServiceImpl implements CityService {

	DatabaseManager<CityDBO> databaseManager = new DatabaseManagerOperation<>();

	@Override
	public long addCity(City city) {
		CityDBO dbo = DTOtoDBOConverter.convert(city);
		dbo = databaseManager.insert(dbo);
		return dbo.getId();
	}

	@Override
	public City getCity(long id) {
		CityDBO dbo = databaseManager.read(id, CityDBO.class);
		City city = DBOtoDTOConverter.convert(dbo);
		return city;
	}

	@Override
	public List<City> search(String key, String value) {
		List<CityDBO> dboList = databaseManager.search(key, value, CityDBO.class);
		List<City> cityList = new ArrayList<>();
		for (CityDBO dbo : dboList) {
			City city = DBOtoDTOConverter.convert(dbo);
			cityList.add(city);
		}
		return cityList;
	}

	@Override
	public boolean deleteCity(long id) {
		return databaseManager.delete(id, CityDBO.class);
	}

}
