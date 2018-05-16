package online.travel.agent.model.dbo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Country")
@Table(name = "country")
public class CountryDBO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CityDBO> cities;

	public CountryDBO() {

	}

	public CountryDBO(Long id, String name, List<CityDBO> cities) {
		this.id = id;
		this.name = name;
		this.cities = cities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
