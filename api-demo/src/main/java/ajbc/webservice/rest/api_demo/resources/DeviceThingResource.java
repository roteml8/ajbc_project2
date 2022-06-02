package ajbc.webservice.rest.api_demo.resources;

import java.util.List;
import java.util.UUID;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.models.Device;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceThingResource {
	
	private DBService dbService = new DBService();
	
	@GET
	public Response getDevicesByThingId(@PathParam("id") UUID id) {
		List<Device> devices = dbService.getDevicesByThingId(id);
		return Response.ok().entity(devices).build();
	}

}
