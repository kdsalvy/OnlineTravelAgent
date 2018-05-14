package online.travel.agent.model.dbo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "City")
@Table(name = "city")
public class CityDBO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private CountryDBO country;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
	private List<HotelDBO> hotels;

	public CityDBO() {
	}

	public CityDBO(String name, CountryDBO country, List<HotelDBO> hotels) {
		this.name = name;
		this.country = country;
		this.hotels = hotels;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountryDBO getCountry() {
		return country;
	}

	public void setCountry(CountryDBO country) {
		this.country = country;
	}

	public List<HotelDBO> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelDBO> hotels) {
		this.hotels = hotels;
	}

}
