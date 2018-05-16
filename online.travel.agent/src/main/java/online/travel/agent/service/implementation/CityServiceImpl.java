package online.travel.agent.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import online.travel.agent.model.db.DatabaseManager;
import online.travel.agent.model.db.DatabaseManagerOperation;
import online.travel.agent.model.db.DatabaseUtil;
import online.travel.agent.model.dbo.CityDBO;
import online.travel.agent.model.dbo.CountryDBO;
import online.travel.agent.model.dbo.HotelDBO;
import online.travel.agent.service.CityService;
import online.travel.agent.view.converter.DBOtoDTOConverter;
import online.travel.agent.view.converter.DTOtoDBOConverter;
import online.travel.agent.view.dto.City;
import online.travel.agent.view.dto.Hotel;

public class CityServiceImpl implements CityService {

    DatabaseManager<CityDBO> databaseManager = new DatabaseManagerOperation<>();
    DatabaseManager<CountryDBO> countryDBManager = new DatabaseManagerOperation<>();

    @Override
    public long addCity(City city) throws Exception {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	CityDBO dbo = null;
	try {
	    entityManager.getTransaction().begin();
	    if (city.getCountry() == null || "".equals(city.getCountry())) {
		throw new Exception("No Country Name Provided");
	    }
	    List<CountryDBO> countryDBOList = countryDBManager.search("Name", city.getCountry(), CountryDBO.class,
		    entityManager);
	    if (countryDBOList == null || countryDBOList.isEmpty())
		throw new Exception("Invalid Country Name");
	    CountryDBO countryDBO = countryDBOList.get(0);
	    dbo = DTOtoDBOConverter.convert(city);
	    dbo.setCountry(countryDBO);
	    List<CityDBO> cityDBOList = countryDBO.getCities();
	    if (cityDBOList == null)
		cityDBOList = new ArrayList<>();
	    cityDBOList.add(dbo);
	    dbo = databaseManager.insert(dbo, entityManager);
	    entityManager.getTransaction().commit();
	} catch (Exception ex) {
	    entityManager.getTransaction().rollback();
	    throw ex;
	} finally {
	    entityManager.close();
	}
	return dbo.getId();
    }

    @Override
    public City getCity(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	City city = null;
	try {
	    CityDBO dbo = databaseManager.read(id, CityDBO.class, entityManager);
	    city = DBOtoDTOConverter.convert(dbo);
	} finally {
	    entityManager.close();
	}
	return city;
    }

    @Override
    public List<City> search(String key, String value) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	List<City> cityList = new ArrayList<>();
	try {
	    List<CityDBO> dboList = databaseManager.search(key, value, CityDBO.class, entityManager);
	    for (CityDBO dbo : dboList) {
		City country = DBOtoDTOConverter.convert(dbo);
		cityList.add(country);
	    }
	} finally {
	    entityManager.close();
	}
	return cityList;
    }

    @Override
    public boolean deleteCity(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	boolean result = false;
	try {
	    entityManager.getTransaction().begin();
	    result = databaseManager.delete(id, CityDBO.class, entityManager);
	    entityManager.getTransaction().commit();
	} finally {
	    entityManager.close();
	}
	return result;
    }

    @Override
    public List<Hotel> getHotelForCity(String cityName) throws Exception {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	List<Hotel> hotelList = new ArrayList<>();
	try {
	    List<CityDBO> dboList = databaseManager.search("name", cityName, CityDBO.class, entityManager);
	    if(dboList == null || dboList.isEmpty())
		throw new Exception("City Not Found");
	    List<HotelDBO> hotelDboList = dboList.get(0).getHotels();
	    for (HotelDBO dbo : hotelDboList) {
		Hotel hotel = DBOtoDTOConverter.convert(dbo);
		hotelList.add(hotel);
	    }
	} finally {
	    entityManager.close();
	}
	return hotelList;
    }

}
