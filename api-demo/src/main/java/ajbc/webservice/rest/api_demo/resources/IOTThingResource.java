package ajbc.webservice.rest.api_demo.resources;

import java.util.List;
import java.util.UUID;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.beans.HardwareFilterBean;
import ajbc.webservice.rest.api_demo.exception.MissingDataException;
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
	@Path("/{uuid}")
	public IOTThing getThingById(@PathParam("uuid") UUID id) {
		return dbService.getThingByID(id);
	}
	
	@GET
	public List<IOTThing> getThingsByProperty(@BeanParam HardwareFilterBean thingFilter)
	{
		if (thingFilter.getManufacturer() != null)
			return dbService.getThingsByManufacturer(thingFilter.getManufacturer());
		if (thingFilter.getModel() != null)
			return dbService.getThingsByModel(thingFilter.getModel());
		if (thingFilter.getType() != null)
			return dbService.getThingsByType(thingFilter.getType());
		return dbService.getAllThings();
	}


	@Path("/{id}/devices")
	public DeviceThingResource getDeviceThingResource() {
		return new DeviceThingResource();
	}
}
