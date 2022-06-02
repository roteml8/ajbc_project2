package ajbc.webservice.rest.api_demo.resources;

import java.util.List;
import java.util.UUID;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.beans.HardwareFilterBean;
import ajbc.webservice.rest.api_demo.models.Device;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {
	
	private DBService dbService = new DBService();
	
	@GET
	@Path("/{id}")
	public Response getDeviceById(@PathParam("id") String id) {
		UUID parsed = UUID.fromString(id);
		return Response.ok().entity(dbService.getDeviceById(parsed)).build();
	}
	
	@GET
	public Response getDevicesByProperty(@BeanParam HardwareFilterBean thingFilter)
	{
		List<Device> devices;
		if (thingFilter.getManufacturer() != null)
			devices = dbService.getDevicesByManufacturer(thingFilter.getManufacturer());
		else if (thingFilter.getModel() != null)
			devices = dbService.getDevicesByModel(thingFilter.getModel());
		else if (thingFilter.getType() != null)
			devices = dbService.getDevicesByType(thingFilter.getType());
		else
			devices = dbService.getAllDevices();
		return Response.ok().entity(devices).build();
	}

}
