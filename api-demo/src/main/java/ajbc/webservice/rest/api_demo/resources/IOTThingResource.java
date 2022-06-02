package ajbc.webservice.rest.api_demo.resources;

import java.util.List;
import java.util.UUID;
import jakarta.ws.rs.core.Response;
import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.beans.HardwareFilterBean;
import ajbc.webservice.rest.api_demo.models.IOTThing;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("things")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IOTThingResource {
	
	private DBService dbService = new DBService();
	
	
	@GET
	@Path("/{id}")
	public Response getThingById(@PathParam("id") String id) {
		UUID parsed = UUID.fromString(id);
		return Response.ok().entity(dbService.getThingByID(parsed)).build();
	}
	
	
	@GET
	public Response getThingsByProperty(@BeanParam HardwareFilterBean thingFilter)
	{
		List<IOTThing> things;
		if (thingFilter.getManufacturer() != null)
			things = dbService.getThingsByManufacturer(thingFilter.getManufacturer());
		else if (thingFilter.getModel() != null)
			things = dbService.getThingsByModel(thingFilter.getModel());
		else if (thingFilter.getType() != null)
			things = dbService.getThingsByType(thingFilter.getType());
		else
			things = dbService.getAllThings();
		return Response.ok().entity(things).build();
	}


	@Path("/{id}/devices")
	public DeviceThingResource getDeviceThingResource() {
		return new DeviceThingResource();
	}
}
