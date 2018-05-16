package online.travel.agent.service;

import java.util.List;

import online.travel.agent.view.dto.Amenity;
import online.travel.agent.view.dto.Hotel;

public interface HotelService {
	public long addHotel(Hotel hotel) throws Exception;

	public Hotel getHotel(long id);

	public List<Hotel> search(String key, String value);

	public boolean deleteHotel(long id);

	public List<Amenity> getAmenitiesForHotel(String hotelName);

	public boolean deleteAmenityFromHotel(String hotelName, String amenityName) throws Exception;

	public Hotel updateHotel(Hotel hotel) throws Exception;
}
