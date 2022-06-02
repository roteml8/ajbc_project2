package ajbc.webservice.rest.api_demo.resources;

import java.util.List;
import java.util.UUID;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.beans.HardwareFilterBean;
import ajbc.webservice.rest.api_demo.models.Device;
import ajbc.webservice.rest.api_demo.models.IOTThing;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {
	
	private DBService dbService = new DBService();
	
	@GET
	@Path("/{id}")
	public Device getDeviceById(@PathParam("id") UUID id) {
		return dbService.getDeviceById(id);
	}
	
	@GET
	public List<Device> getDevicesByProperty(@BeanParam HardwareFilterBean thingFilter)
	{
		if (thingFilter.getManufacturer() != null)
			return dbService.getDevicesByManufacturer(thingFilter.getManufacturer());
		if (thingFilter.getModel() != null)
			return dbService.getDevicesByModel(thingFilter.getModel());
		if (thingFilter.getType() != null)
			return dbService.getDevicesByType(thingFilter.getType());
		return dbService.getAllDevices();
	}

}
