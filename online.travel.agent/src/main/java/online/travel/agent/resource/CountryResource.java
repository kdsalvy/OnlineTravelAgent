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

import online.travel.agent.service.CountryService;
import online.travel.agent.service.implementation.CountryServiceImpl;
import online.travel.agent.view.dto.Country;

@Path("/country")
public class CountryResource {
    private static Logger LOG = Log.getLogger(CountryResource.class);
    CountryService countryService = new CountryServiceImpl();

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCountry(Country country) {
	Response response = null;
	try {
	    long id = countryService.addCountry(country);
	    LOG.info("Country Added with id: " + id);
	    response = Response.created(URI.create("/country/get/" + id)).entity(id).build();
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent().entity("Exception in adding country: " + e.getMessage()).build();
	}
	return response;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("id") int id) {
	Response response = null;
	try {
	    Country result = countryService.getCountry(id);
	    if (result != null) {
		LOG.info("Country Found with id: " + id);
		response = Response.ok().entity(result).build();
	    } else {
		LOG.warn("Could not find Country with id: " + id);
		response = Response.noContent().entity("Country with given id is not found").build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent().entity("Exception in fetching the country with given id: " + e.getMessage())
		    .build();
	}
	return response;
    }

    @DELETE
    @Path("/remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCountry(@PathParam("id") int id) {
	Response response = null;
	try {
	    boolean result = countryService.deleteCountry(id);
	    if (result) {
		LOG.info("Deleted the country with id: " + id);
		response = Response.ok().build();
	    } else {
		LOG.warn("No Country found with id: " + id);
		response = Response.noContent().entity("Country with given id is not found").build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent().entity("Exception in deleting the country with given id: " + e.getMessage())
		    .build();
	}
	return response;
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCountry(@QueryParam("key") String key, @QueryParam("value") String value) {
	Response response = null;
	try {
	    List<Country> country = countryService.search(key, value);
	    if (country == null || country.isEmpty()) {
		LOG.warn("Country with given query parameters are not found");
		response = Response.noContent().entity("No Content Found").build();
	    } else {
		LOG.info("Country with given query parameters are found");
		response = Response.ok().entity(country).build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent()
		    .entity("Exception in searching the country with given query parameters: " + e.getMessage())
		    .build();
	}
	return response;
    }
}
