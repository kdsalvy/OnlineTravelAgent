package online.travel.agent.service;

import java.util.List;

import online.travel.agent.view.dto.Hotel;

public interface HotelService {
	public long addHotel(Hotel hotel);

	public Hotel getHotel(long id);

	public List<Hotel> search(String key, String value);

	public boolean deleteHotel(long id);
}
