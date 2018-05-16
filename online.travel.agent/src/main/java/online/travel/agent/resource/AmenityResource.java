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

import online.travel.agent.service.AmenityService;
import online.travel.agent.service.implementation.AmenityServiceImpl;
import online.travel.agent.view.dto.Amenity;

@Path("amenity")
public class AmenityResource {
    private static Logger LOG = Log.getLogger(AmenityResource.class);
    private static AmenityService amenityService = new AmenityServiceImpl();

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAmenity(Amenity amenity) {
	Response response = null;
	try {
	    long id = amenityService.addAmenity(amenity);
	    LOG.info("Amenity Added with id: " + id);
	    response = Response.created(URI.create("/amenity/get/" + id)).entity(id).build();
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent().entity("Exception in adding amenity: " + e.getMessage()).build();
	}
	return response;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAmenity(@PathParam("id") int id) {
	Response response = null;
	try {
	    Amenity result = amenityService.getAmenity(id);
	    if (result != null) {
		LOG.info("Amenity Found with id: " + id);
		response = Response.ok().entity(result).build();
	    } else {
		LOG.warn("Could not find Amenity with id: " + id);
		response = Response.noContent().entity("Amenity with given id is not found").build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent().entity("Exception in fetching the amenity with given id: " + e.getMessage())
		    .build();
	}
	return response;
    }

    @DELETE
    @Path("/remove/{hotelName}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAmenity(@PathParam("id") int id) {
	Response response = null;
	try {
	    boolean result = amenityService.deleteAmenity(id);
	    if (result) {
		LOG.info("Deleted the amenity with id: " + id);
		response = Response.ok().build();
	    } else {
		LOG.warn("No Amenity found with id: " + id);
		response = Response.noContent().entity("Amenity with given id is not found").build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent().entity("Exception in deleting the amenity with given id: " + e.getMessage())
		    .build();
	}
	return response;
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAmenity(@QueryParam("key") String key, @QueryParam("value") String value) {
	Response response = null;
	try {
	    List<Amenity> amenity = amenityService.search(key, value);
	    if (amenity == null || amenity.isEmpty()) {
		LOG.warn("Amenity with given query parameters are not found");
		response = Response.noContent().entity("No Content Found").build();
	    } else {
		LOG.info("Amenity with given query parameters are found");
		response = Response.ok().entity(amenity).build();
	    }
	} catch (Exception e) {
	    LOG.warn(e);
	    response = Response.noContent()
		    .entity("Exception in searching the amenity with given query parameters: " + e.getMessage())
		    .build();
	}
	return response;
    }
}
