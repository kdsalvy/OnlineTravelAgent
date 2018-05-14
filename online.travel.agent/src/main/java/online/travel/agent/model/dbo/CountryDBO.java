package online.travel.agent.model.dbo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Country")
public class CountryDBO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "City")
	private List<CityDBO> cities;

	public CountryDBO() {

	}

	public CountryDBO( String name, List<CityDBO> cities) {
		this.name = name;
		this.cities = cities;
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

	public List<CityDBO> getCities() {
		return cities;
	}

	public void setCities(List<CityDBO> cities) {
		this.cities = cities;
	}

}
