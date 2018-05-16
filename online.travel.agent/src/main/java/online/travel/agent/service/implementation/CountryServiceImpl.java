package online.travel.agent.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import online.travel.agent.model.db.DatabaseManager;
import online.travel.agent.model.db.DatabaseManagerOperation;
import online.travel.agent.model.db.DatabaseUtil;
import online.travel.agent.model.dbo.CountryDBO;
import online.travel.agent.service.CountryService;
import online.travel.agent.view.converter.DBOtoDTOConverter;
import online.travel.agent.view.converter.DTOtoDBOConverter;
import online.travel.agent.view.dto.Country;

public class CountryServiceImpl implements CountryService {

    private DatabaseManager<CountryDBO> databaseManager = new DatabaseManagerOperation<>();

    @Override
    public long addCountry(Country country) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	CountryDBO dbo = null;
	try {
	    entityManager.getTransaction().begin();
	    dbo = DTOtoDBOConverter.convert(country);
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
    public Country getCountry(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	Country country = null;
	try {
	    CountryDBO dbo = databaseManager.read(id, CountryDBO.class, entityManager);
	    country = DBOtoDTOConverter.convert(dbo);
	} finally {
	    entityManager.close();
	}
	return country;
    }

    @Override
    public List<Country> search(String key, String value) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	List<Country> countryList = new ArrayList<>();
	try {
	    List<CountryDBO> dboList = databaseManager.search(key, value, CountryDBO.class, entityManager);
	    for (CountryDBO dbo : dboList) {
		Country country = DBOtoDTOConverter.convert(dbo);
		countryList.add(country);
	    }
	} finally {
	    entityManager.close();
	}
	return countryList;
    }

    @Override
    public boolean deleteCountry(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	boolean result = false;
	try {
	    entityManager.getTransaction().begin();
	    result = databaseManager.delete(id, CountryDBO.class, entityManager);
	    entityManager.getTransaction().commit();
	} finally {
	    entityManager.close();
	}
	return result;
    }

}
