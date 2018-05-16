package online.travel.agent.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import online.travel.agent.model.db.DatabaseManager;
import online.travel.agent.model.db.DatabaseManagerOperation;
import online.travel.agent.model.db.DatabaseUtil;
import online.travel.agent.model.dbo.AmenityDBO;
import online.travel.agent.model.dbo.CityDBO;
import online.travel.agent.model.dbo.HotelDBO;
import online.travel.agent.service.HotelService;
import online.travel.agent.view.converter.DBOtoDTOConverter;
import online.travel.agent.view.converter.DTOtoDBOConverter;
import online.travel.agent.view.dto.Amenity;
import online.travel.agent.view.dto.Hotel;

public class HotelServiceImpl implements HotelService {

    DatabaseManager<HotelDBO> databaseManager = new DatabaseManagerOperation<>();
    DatabaseManager<CityDBO> cityDBManager = new DatabaseManagerOperation<>();
    DatabaseManager<AmenityDBO> amenityDBManager = new DatabaseManagerOperation<>();

    @Override
    public long addHotel(Hotel hotel) throws Exception {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	HotelDBO dbo = null;
	try {
	    entityManager.getTransaction().begin();
	    if (hotel.getCity() == null || "".equals(hotel.getCity())) {
		throw new Exception("No City Name Provided");
	    }
	    List<CityDBO> cityDBOList = cityDBManager.search("Name", hotel.getCity(), CityDBO.class, entityManager);
	    if (cityDBOList == null || cityDBOList.isEmpty())
		throw new Exception("Invalid City Name");
	    CityDBO cityDBO = cityDBOList.get(0);
	    dbo = DTOtoDBOConverter.convert(hotel);
	    dbo.setCity(cityDBO);
	    List<HotelDBO> hotelDBOList = cityDBO.getHotels();
	    if (hotelDBOList == null)
		hotelDBOList = new ArrayList<>();
	    hotelDBOList.add(dbo);
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
    public Hotel getHotel(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	Hotel hotel = null;
	try {
	    HotelDBO dbo = databaseManager.read(id, HotelDBO.class, entityManager);
	    hotel = DBOtoDTOConverter.convert(dbo);
	} finally {
	    entityManager.close();
	}
	return hotel;
    }

    @Override
    public List<Hotel> search(String key, String value) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	List<Hotel> hotelList = new ArrayList<>();
	try {
	    List<HotelDBO> dboList = databaseManager.search(key, value, HotelDBO.class, entityManager);
	    for (HotelDBO dbo : dboList) {
		Hotel country = DBOtoDTOConverter.convert(dbo);
		hotelList.add(country);
	    }
	} finally {
	    entityManager.close();
	}
	return hotelList;
    }

    @Override
    public boolean deleteHotel(long id) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	boolean result = false;
	try {
	    entityManager.getTransaction().begin();
	    result = databaseManager.delete(id, HotelDBO.class, entityManager);
	    entityManager.getTransaction().commit();
	} finally {
	    entityManager.close();
	}
	return result;
    }

    @Override
    public List<Amenity> getAmenitiesForHotel(String hotelName) {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	List<Amenity> amenityList = new ArrayList<>();
	try {
	    List<HotelDBO> dboList = databaseManager.search("name", hotelName, HotelDBO.class, entityManager);
	    if (dboList != null && !dboList.isEmpty()) {
		List<AmenityDBO> amenityDBOs = dboList.get(0).getAmenities();
		for (AmenityDBO amenityDbo : amenityDBOs) {
		    amenityList.add(DBOtoDTOConverter.convert(amenityDbo));
		}
	    }
	} finally {
	    entityManager.close();
	}
	return amenityList;
    }

    @Override
    public boolean deleteAmenityFromHotel(String hotelName, String amenityName) throws Exception {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	try {
	    entityManager.getTransaction().begin();
	    List<HotelDBO> dboList = databaseManager.search("name", hotelName, HotelDBO.class, entityManager);
	    List<AmenityDBO> amenityDBOList = amenityDBManager.search("name", amenityName, AmenityDBO.class,
		    entityManager);
	    if (dboList != null && !dboList.isEmpty() && amenityDBOList != null && !amenityDBOList.isEmpty()) {
		HotelDBO hotelDBO = dboList.get(0);
		AmenityDBO amenityDBO = amenityDBOList.get(0);
		if (!hotelDBO.getAmenities().contains(amenityDBO))
		    throw new Exception("Amenity doesn't exist in the Hotel");
		else {
		    List<AmenityDBO> amenityDBOs = hotelDBO.getAmenities();
		    amenityDBOs.remove(amenityDBO);
		    hotelDBO.setAmenities(amenityDBOs);
		}
	    }
	    entityManager.getTransaction().commit();
	    return true;
	} finally {
	    entityManager.close();
	}
    }

    @Override
    public Hotel updateHotel(Hotel hotel) throws Exception {
	EntityManager entityManager = DatabaseUtil.getEntityManager();
	HotelDBO hotelDBO = null;
	try {
	    entityManager.getTransaction().begin();
	    List<HotelDBO> dboList = databaseManager.search("name", hotel.getName(), HotelDBO.class, entityManager);
	    if (dboList == null || dboList.isEmpty())
		throw new Exception("No Hotels Found");
	    hotelDBO = dboList.get(0);
	    hotelDBO = updateHotelEntity(hotelDBO, hotel);
	} finally {
	    entityManager.close();
	}
	return DBOtoDTOConverter.convert(hotelDBO);
    }

    private HotelDBO updateHotelEntity(HotelDBO hotelDBO, Hotel hotel) {
	hotelDBO.setActive(hotel.isActive());
	hotelDBO.setAddress(hotel.getAddress());
	hotelDBO.setContact(hotel.getContact());
	hotelDBO.setDescription(hotel.getDescription());
	hotelDBO.setName(hotel.getName());
	return hotelDBO;
    }

}
