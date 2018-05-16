package online.travel.agent.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import online.travel.agent.model.db.DatabaseManager;
import online.travel.agent.model.db.DatabaseManagerOperation;
import online.travel.agent.model.db.DatabaseUtil;
import online.travel.agent.model.dbo.AmenityDBO;
import online.travel.agent.model.dbo.HotelDBO;
import online.travel.agent.service.AmenityService;
import online.travel.agent.view.converter.DBOtoDTOConverter;
import online.travel.agent.view.converter.DTOtoDBOConverter;
import online.travel.agent.view.dto.Amenity;

public class AmenityServiceImpl implements AmenityService {

    DatabaseManager<AmenityDBO> databaseManager = new DatabaseManagerOperation<>();
    DatabaseManager<HotelDBO> hotelDBManager = new DatabaseManagerOperation<>();

    @Override
    public long addAmenity(Amenity amenity) throws Exception {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	AmenityDBO dbo = null;
	try {
	    entityManager.getTransaction().begin();
	    if (amenity.getHotelName() == null || "".equals(amenity.getHotelName())) {
		throw new Exception("No Hotel Name Provided");
	    }
	    List<HotelDBO> hotelDBOList = hotelDBManager.search("Name", amenity.getHotelName(), HotelDBO.class,
		    entityManager);
	    if (hotelDBOList == null || hotelDBOList.isEmpty())
		throw new Exception("Invalid Hotel Name");
	    HotelDBO hotelDBO = hotelDBOList.get(0);
	    dbo = DTOtoDBOConverter.convert(amenity);
	    List<AmenityDBO> amenityDBOList = hotelDBO.getAmenities();
	    if (amenityDBOList == null)
		amenityDBOList = new ArrayList<>();
	    amenityDBOList.add(dbo);
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
    public Amenity getAmenity(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	Amenity amenity = null;
	try {
	    AmenityDBO dbo = databaseManager.read(id, AmenityDBO.class, entityManager);
	    amenity = DBOtoDTOConverter.convert(dbo);
	} finally {
	    entityManager.close();
	}
	return amenity;
    }

    @Override
    public List<Amenity> search(String key, String value) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	Amenity amenity = null;
	List<Amenity> amenityList = new ArrayList<>();
	try {
	    List<AmenityDBO> amenityDBOs = databaseManager.search(key.toLowerCase(), value, AmenityDBO.class,
		    entityManager);
	    for (AmenityDBO amenityDbo : amenityDBOs) {
		amenity = DBOtoDTOConverter.convert(amenityDbo);
		amenityList.add(amenity);
	    }
	} finally {
	    entityManager.close();
	}
	return amenityList;
    }

    @Override
    public boolean deleteAmenity(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	boolean result = false;
	try {
	    entityManager.getTransaction().begin();
	    databaseManager.delete(id, AmenityDBO.class, entityManager);
	    entityManager.getTransaction().commit();
	} finally {
	    entityManager.close();
	}
	return result;
    }

}
