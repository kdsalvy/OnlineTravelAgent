package online.travel.agent.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import online.travel.agent.service.HotelService;
import online.travel.agent.service.implementation.HotelServiceImpl;
import online.travel.agent.view.dto.Amenity;
import online.travel.agent.view.dto.Hotel;

@Path("hotel")
public class HotelResource {
	private static Logger LOG = Log.getLogger(HotelResource.class);
	HotelService hotelService = new HotelServiceImpl();

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addHotel(Hotel hotel) {
		Response response = null;
		try {
			long id = hotelService.addHotel(hotel);
			LOG.info("Hotel Added with id: " + id);
			response = Response.created(URI.create("/hotel/get/" + id)).entity(id).build();
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError().entity("Exception in adding hotel: " + e.getMessage()).build();
		}
		return response;
	}

	@GET
	@Path("get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHotel(@PathParam("id") int id) {
		Response response = null;
		try {
			Hotel result = hotelService.getHotel(id);
			if (result != null) {
				LOG.info("Hotel Found with id: " + id);
				response = Response.ok().entity(result).build();
			} else {
				LOG.warn("Could not find Hotel with id: " + id);
				response = Response.serverError().entity("Hotel with given id is not found").build();
			}
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError().entity("Exception in fetching the hotel with given id: " + e.getMessage())
					.build();
		}
		return response;
	}

	@DELETE
	@Path("remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteHotel(@PathParam("id") int id) {
		Response response = null;
		try {
			boolean result = hotelService.deleteHotel(id);
			if (result) {
				LOG.info("Deleted the hotel with id: " + id);
				response = Response.ok().build();
			} else {
				LOG.warn("No Hotel found with id: " + id);
				response = Response.serverError().entity("Hotel with given id is not found").build();
			}
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError().entity("Exception in deleting the hotel with given id: " + e.getMessage())
					.build();
		}
		return response;
	}

	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchHotels(@QueryParam("key") String key, @QueryParam("value") String value) {
		Response response = null;
		try {
			List<Hotel> hotel = hotelService.search(key, value);
			if (hotel == null || hotel.isEmpty()) {
				LOG.warn("Hotel with given query parameters are not found");
				response = Response.serverError().entity("No Content Found").build();
			} else {
				LOG.info("Hotel with given query parameters are found");
				response = Response.ok().entity(hotel).build();
			}
		} catch (Exception e) {
			LOG.warn(e);
			response = Response.serverError()
					.entity("Exception in searching the hotel with given query parameters: " + e.getMessage()).build();
		}
		return response;
	}

	@GET
	@Path("{hotelName}/amenities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAmenitiesForHotel(@PathParam("hotelName") String hotelName) {
		Response response = null;
		try {
			List<Amenity> amenities = hotelService.getAmenitiesForHotel(hotelName);
			response = Response.ok().entity(amenities).build();
		} catch (Exception ex) {
			LOG.warn(ex);
			response = Response.serverError()
					.entity("Exception in searching amenities for the hotel with given Hotel Name: " + ex.getMessage())
					.build();
		}
		return response;
	}

	@DELETE
	@Path("{hotelName}/amenity/{amenityName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAmenityFromHotel(@PathParam("hotelName") String hotelName,
			@PathParam("amenityName") String amenityName) {
		Response response = null;
		try {
			boolean result = hotelService.deleteAmenityFromHotel(hotelName, amenityName);
			if (result)
				response = Response.ok().build();
			else
				response = Response.serverError().entity("Couldn't find/delete the amenity").build();
		} catch (Exception ex) {
			LOG.warn(ex);
			response = Response.serverError()
					.entity("Exception in deleting the amenity for the hotel with given Hotel Name: " + ex.getMessage())
					.build();
		}
		return response;
	}

	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateHotel(Hotel hotel) {
		Response response = null;
		try {
			Hotel updatedHotel = hotelService.updateHotel(hotel);
			response = Response.ok().entity(updatedHotel).build();
		} catch (Exception ex) {
			LOG.warn(ex);
			response = Response.serverError().entity("Exception in updating hotel: " + ex.getMessage()).build();
		}
		return response;
	}

	@POST
	@Path("{hotelName}/add/amenity")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAmenityToHotel(@PathParam("hotelName") String hotelName, Amenity amenity) {
		Response response = null;
		try {
			hotelService.addAmenityToHotel(amenity, hotelName);
			response = Response.ok().build();
		} catch (Exception ex) {
			LOG.warn(ex);
			response = Response.serverError().entity("Exception while adding amenity to hotel: " + ex.getMessage())
					.build();
		}
		return response;
	}
}
