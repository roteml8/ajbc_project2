package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.demoDB.DBMock;
import ajbc.webservice.rest.api_demo.exception.MissingDataException;
import ajbc.webservice.rest.api_demo.models.Device;
import ajbc.webservice.rest.api_demo.models.IOTThing;
import ajbc.webservice.rest.api_demo.models.Type;

class DBServiceTest {
	
	private DBService service = new DBService();
	private DBMock db = DBMock.getInstance();
	
	@Test
	public void testGetAllThings()
	{
		List<IOTThing> serviceThings = service.getAllThings();
		List<IOTThing> dbThings = db.getIotThingsMap().values().stream().toList();
		assertEquals(dbThings, serviceThings);
		
	}
	
	@Test
	public void testGetThingById()
	{
		List<UUID> ids = db.getIotThingsMap().keySet().stream().toList();
		UUID id = ids.get(0);
		IOTThing expected = db.getIotThingsMap().get(id);
		IOTThing actual = service.getThingByID(id);
		assertEquals(expected, actual);
		
		UUID wrongId = UUID.randomUUID();
		assertThrows( MissingDataException.class, ()->service.getThingByID(wrongId));
		
	}
	
	@Test
	public void testUpdateThing()
	{
		IOTThing newThing = new IOTThing("New","New2",Type.ACTUATOR);
		UUID thingId = newThing.getID();
		List<Device> thingDevices = new ArrayList<>();
		thingDevices.add(new Device("NewDev", "NewDev2",Type.CONTROLLER));
		newThing.setDevices(thingDevices);
		
		// first time: thing doesnt exist in db, so it is added
		service.updateThing(thingId, newThing); 
		
		IOTThing fromDB = service.getThingByID(thingId);
		
		// the new thing and its device were added to the DB
		assertEquals(fromDB, newThing);
		assertTrue(service.getAllThings().contains(newThing));
		assertTrue(service.getAllDevices().containsAll(newThing.getDevices()));
		
		// second time: update new thing devices 
		IOTThing replace = new IOTThing("New","New2",Type.ACTUATOR);
		List<Device> replaceDevices = new ArrayList<>();
		replaceDevices.add(new Device("Replace", "Replace2",Type.CONTROLLER));
		replace.setDevices(replaceDevices);
		
		service.updateThing(fromDB.getID(), replace); 
		
		List<Device> allDevices = service.getAllDevices();
		
		// the new device was added to the list of all devices
		assertTrue(allDevices.containsAll(fromDB.getDevices()));
		
		// collect the devices from all things
		List<Device> thingsDevices = new ArrayList<>();
		service.getAllThings().stream().forEach(t->thingsDevices.addAll(t.getDevices()));
		
		// sort the 2 lists so they contain the elements in the same order
		thingsDevices.sort((d1,d2)->d1.getID().compareTo(d2.getID()));
		allDevices.sort((d1,d2)->d1.getID().compareTo(d2.getID()));
		
		
		// the "old" device was removed from the list of all devices
		// both lists contain the same devices as they should
		assertEquals(thingsDevices, allDevices);

	}
	

}
