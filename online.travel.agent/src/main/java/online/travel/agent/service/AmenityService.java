package online.travel.agent.service;

import java.util.List;

import online.travel.agent.view.dto.Amenity;

public interface AmenityService {

    public long addAmenity(Amenity amenity) throws Exception;

    public Amenity getAmenity(long id);

    public boolean deleteAmenity(long id);

    public List<Amenity> search(String key, String value);

}
