package ajbc.webservice.rest.api_demo.DBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import ajbc.webservice.rest.api_demo.demoDB.DBMock;
import ajbc.webservice.rest.api_demo.exception.MissingDataException;
import ajbc.webservice.rest.api_demo.models.Device;
import ajbc.webservice.rest.api_demo.models.IOTThing;
import ajbc.webservice.rest.api_demo.models.Type;

public class DBService {
	
	private DBMock db;
	private Map<UUID, IOTThing> things;
	private Map<UUID, Device> devices; 
	
	public DBService()
	{
		db = DBMock.getInstance();
		things = db.getIotThingsMap();
		devices = db.getDevicesMap();
				
	}
	
	// Things
	
	public List<IOTThing> getAllThings()
	{
		return new ArrayList<IOTThing>(things.values());
	}
	
	public IOTThing getThingByID(UUID id)
	{
		IOTThing current = things.get(id);
		if (current == null)
			throw new MissingDataException("No IOTThing with the given ID.");
		return current;
	}
	
	public IOTThing updateThing(UUID id, IOTThing updated)
	{
		if (things.containsKey(id))
		{
			IOTThing current = things.get(id);
			List<Device> oldDevices = current.getDevices();
			current.setDevices(updated.getDevices());
			things.put(id, current);
			updateDevices(current, oldDevices);
			return current;
		}
		else // new thing
			addNewThing(id,updated);
		
		return updated;
	}
	
	public void addNewThing(UUID id, IOTThing newThing)
	{
		things.put(id, newThing);
		addDevices(newThing.getDevices());
	}
	

	
	public List<IOTThing> getThingsByType(Type type)
	{
		return things.values().stream().filter(t->t.getType().equals(type)).toList();
	}
	
	public List<IOTThing> getThingsByModel(String model)
	{
		return things.values().stream().filter(t->t.getModel().equals(model)).toList();

	}
	
	
	public List<IOTThing> getThingsByManufacturer(String manufacturer)
	{
		return things.values().stream().filter(t->t.getManufacturer().equals(manufacturer)).toList();

	}
	
	
	// Devices
	
	
	public List<Device> getAllDevices()
	{
		return new ArrayList<>(devices.values());
	}
	public Device getDeviceById(UUID id)
	{
		Device current = devices.get(id);
		if (current == null)
			throw new MissingDataException("No Device with the given ID.");
		return current;
	}
	
	public void updateDevices(IOTThing updated, List<Device> oldDevices)
	{
		List<Device> newDevices = updated.getDevices();
		for (Device d: oldDevices)
			if (!newDevices.contains(d))
				devices.remove(d.getID());
	}
	
	public boolean isDeviceConnectedToThing(Device device)
	{
		for (IOTThing thing: things.values())
			if (thing.getDevices().contains(device))
				return true;
		return false;
	}
	
	public List<Device> getDevicesByType(Type type)
	{
		return devices.values().stream().filter(t->t.getType().equals(type)).toList();

	}
	
	public List<Device> getDevicesByModel(String model)
	{
		return devices.values().stream().filter(t->t.getModel().equals(model)).toList();

	}
	
	public List<Device> getDevicesByManufacturer(String manufacturer)
	{
		return devices.values().stream().filter(t->t.getManufacturer().equals(manufacturer)).toList();

	}
	
	public List<Device> getDevicesByThingId(UUID thingId)
	{
		return things.get(thingId).getDevices();
	}
	
	public void addDevices(List<Device> newDevices)
	{
		newDevices.forEach(t->devices.put(t.getID(), t));
	}
	

}
