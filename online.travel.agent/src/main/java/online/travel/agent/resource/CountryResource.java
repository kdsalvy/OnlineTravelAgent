package online.travel.agent.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.travel.agent.service.CountryService;
import online.travel.agent.view.dto.Country;

@Path("/country")
public class CountryResource {
	CountryService countryService = new CountryService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCountry(Country country){
		long id = countryService.addCountry(country);
	}
}
