package online.travel.agent.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import online.travel.agent.service.CityService;
import online.travel.agent.service.implementation.CityServiceImpl;
import online.travel.agent.view.dto.City;
import online.travel.agent.view.dto.Hotel;

@Path("city")
public class CityResource {
	private static Logger LOG = Log.getLogger(CityResource.class);
	private static CityService cityService = new CityServiceImpl();

	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCity(City city) {
		Response response = null;
		try {
			long id = cityService.addCity(city);
			LOG.info("City Added with id: " + id);
			response = Response.created(URI.create("/city/get/" + id)).entity(id).build();
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError().entity("Exception in adding city: " + e.getMessage()).build();
		}
		return response;
	}

	@GET
	@Path("get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCity(@PathParam("id") int id) {
		Response response = null;
		try {
			City result = cityService.getCity(id);
			if (result != null) {
				LOG.info("City Found with id: " + id);
				response = Response.ok().entity(result).build();
			} else {
				LOG.warn("Could not find City with id: " + id);
				response = Response.serverError().entity("City with given id is not found").build();
			}
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError().entity("Exception in fetching the city with given id: " + e.getMessage())
					.build();
		}
		return response;
	}

	@DELETE
	@Path("remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCity(@PathParam("id") int id) {
		Response response = null;
		try {
			boolean result = cityService.deleteCity(id);
			if (result) {
				LOG.info("Deleted the city with id: " + id);
				response = Response.ok().build();
			} else {
				LOG.warn("No City found with id: " + id);
				response = Response.serverError().entity("City with given id is not found").build();
			}
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError().entity("Exception in deleting the city with given id: " + e.getMessage())
					.build();
		}
		return response;
	}

	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchCities(@QueryParam("key") String key, @QueryParam("value") String value) {
		Response response = null;
		try {
			List<City> city = cityService.search(key, value);
			if (city == null || city.isEmpty()) {
				LOG.warn("City with given query parameters are not found");
				response = Response.serverError().entity("No Content Found").build();
			} else {
				LOG.info("City with given query parameters are found");
				response = Response.ok().entity(city).build();
			}
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError()
					.entity("Exception in searching the city with given query parameters: " + e.getMessage()).build();
		}
		return response;
	}

	@GET
	@Path("{city}/hotels")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHotelForCity(@PathParam("city") String cityName) {
		Response response = null;
		List<Hotel> hotelList = null;
		try {
			hotelList = cityService.getHotelForCity(cityName);
			if (hotelList == null || hotelList.isEmpty()) {
				LOG.warn("No Hotels Found In Given City");
				response = Response.serverError().entity("No Content Found").build();
			} else {
				LOG.info("Hotels for given city name was found");
				response = Response.ok().entity(hotelList).build();
			}
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError()
					.entity("Exception in searching the city with given query parameters: " + e.getMessage()).build();
		}
		return response;
	}
}
