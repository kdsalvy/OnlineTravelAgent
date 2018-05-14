package online.travel.agent.service.implementation;

import java.util.ArrayList;
import java.util.List;

import online.travel.agent.model.db.DatabaseManager;
import online.travel.agent.model.db.DatabaseManagerOperation;
import online.travel.agent.model.dbo.HotelDBO;
import online.travel.agent.service.HotelService;
import online.travel.agent.view.converter.DBOtoDTOConverter;
import online.travel.agent.view.converter.DTOtoDBOConverter;
import online.travel.agent.view.dto.Hotel;

public class HotelServiceImpl implements HotelService {

	DatabaseManager<HotelDBO> databaseManager = new DatabaseManagerOperation<>();

	@Override
	public long addHotel(Hotel hotel) {
		HotelDBO dbo = DTOtoDBOConverter.convert(hotel);
		dbo = databaseManager.insert(dbo);
		return dbo.getId();
	}

	@Override
	public Hotel getHotel(long id) {
		HotelDBO dbo = databaseManager.read(id, HotelDBO.class);
		Hotel hotel = DBOtoDTOConverter.convert(dbo);
		return hotel;
	}

	@Override
	public List<Hotel> search(String key, String value) {
		List<HotelDBO> dboList = databaseManager.search(key, value, HotelDBO.class);
		List<Hotel> hotelList = new ArrayList<>();
		for (HotelDBO dbo : dboList) {
			Hotel hotel = DBOtoDTOConverter.convert(dbo);
			hotelList.add(hotel);
		}
		return hotelList;
	}

	@Override
	public boolean deleteHotel(long id) {
		return databaseManager.delete(id, HotelDBO.class);
	}

}
