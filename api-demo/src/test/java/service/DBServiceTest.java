package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
	
	@Test
	public void testGetDevicesByThingID()
	{

		IOTThing newThing = new IOTThing("New","New2",Type.ACTUATOR);
		UUID thingId = newThing.getID();
		List<Device> thingDevices = new ArrayList<>();
		Device d = new Device("NewDev", "NewDev2",Type.CONTROLLER);
		thingDevices.add(d);
		newThing.setDevices(thingDevices);
		
		// first time: thing doesnt exist in db, so it is added
		service.updateThing(thingId, newThing); 

		List<Device> newThingDevices = service.getDevicesByThingId(thingId);
		assertEquals(thingDevices, newThingDevices);
		assertTrue(service.getAllDevices().contains(d));

		// try to search for devices by invalid thing id 
		UUID invalidID = UUID.randomUUID();
		assertThrows(MissingDataException.class, ()->service.getDevicesByThingId(invalidID));
		
	}
	
	@Test
	public void testGetThingByModel()
	{
		String model = "ThingModel1";
		List<IOTThing> things = service.getThingsByModel(model);
		assertNotNull(things);
		assertEquals(2, things.size());
		List<String> models = things.stream().map(t->t.getModel()).toList();
		List<String> expected = Arrays.asList(model, model);
		assertEquals(expected, models);
		
		String invModel = "invalidModel";
		 things = service.getThingsByModel(invModel);
		 assertTrue(things.isEmpty());		
	}
	
	@Test
	public void testGetThingByManufacturer()
	{
		String manufacturer = "Manufacturer1";
		List<IOTThing> things = service.getThingsByManufacturer(manufacturer);
		assertNotNull(things);
		assertEquals(1, things.size());
		List<String> manufacturers = things.stream().map(t->t.getManufacturer()).toList();
		List<String> expected = Arrays.asList(manufacturer);
		assertEquals(expected, manufacturers);
		
		String manufacturer2 = "Manufacturer2";
		 things = service.getThingsByManufacturer(manufacturer2);
		assertNotNull(things);
		assertEquals(1, things.size());
		manufacturers = things.stream().map(t->t.getManufacturer()).toList();
		expected = Arrays.asList(manufacturer2);
		assertEquals(expected, manufacturers);

		String invManufacturer = "invalid";
		things =  service.getThingsByManufacturer(invManufacturer);
		 assertTrue(things.isEmpty());		
	}
	
	@Test
	public void testGetThingByType()
	{
		Type controller = Type.CONTROLLER;
		Type actuator = Type.ACTUATOR;
		Type sensor = Type.SENSOR;
		
		List<IOTThing> controllers = service.getThingsByType(controller);
		List<IOTThing> actuators = service.getThingsByType(actuator);
		List<IOTThing> sensors = service.getThingsByType(sensor);
		
		assertEquals(2, controllers.size());
		assertEquals(2, actuators.size());
		assertEquals(0, sensors.size());
		
		assertThrows(IllegalArgumentException.class, ()->service.getThingsByType(Type.valueOf("invalid type")));

	}
	
	@Test
	public void testGetAllDevices()
	{
		List<Device> serviceDevices = service.getAllDevices();
		List<Device> dbDevices = db.getDevicesMap().values().stream().toList();
		assertEquals(dbDevices, serviceDevices);
		
	}
	
	@Test
	public void testGetDeviceById()
	{
		List<UUID> ids = db.getDevicesMap().keySet().stream().toList();
		UUID id = ids.get(0);
		Device expected = db.getDevicesMap().get(id);
		Device actual = service.getDeviceById(id);
		assertEquals(expected, actual);
		
		UUID wrongId = UUID.randomUUID();
		assertThrows(MissingDataException.class, ()->service.getDeviceById(wrongId));
	}
	
	@Test
	public void testGetDeviceByModel()
	{
		String model = "Model1";
		List<Device> devices = service.getDevicesByModel(model);
		assertNotNull(devices);
		assertEquals(1, devices.size());
		List<String> models = devices.stream().map(t->t.getModel()).toList();
		List<String> expected = Arrays.asList(model);
		assertEquals(expected, models);
		
		String model2 = "NewDev";
		devices = service.getDevicesByModel(model2);
		assertNotNull(devices);
		assertEquals(1, devices.size());
		models = devices.stream().map(t->t.getModel()).toList();
		expected = Arrays.asList(model2);
		assertEquals(expected, models);

		String invModel = "invalidModel";
		 devices = service.getDevicesByModel(invModel);
		 assertTrue(devices.isEmpty());		
	}
	
	@Test
	public void testGetDevicesByManufacturer()
	{
		String manufacturer = "Manufacturer1";
		List<Device> devices = service.getDevicesByManufacturer(manufacturer);
		assertNotNull(devices);
		assertEquals(1, devices.size());
		List<String> manufacturers = devices.stream().map(t->t.getManufacturer()).toList();
		List<String> expected = Arrays.asList(manufacturer);
		assertEquals(expected, manufacturers);
		
		String invManufacturer = "invalid";
		devices =  service.getDevicesByManufacturer(invManufacturer);
		assertTrue(devices.isEmpty());		
	}
	
	@Test
	public void testGetDevicesByType()
	{
		Type controller = Type.CONTROLLER;
		Type actuator = Type.ACTUATOR;
		Type sensor = Type.SENSOR;
		
		List<Device> controllers = service.getDevicesByType(controller);
		List<Device> actuators = service.getDevicesByType(actuator);
		List<Device> sensors = service.getDevicesByType(sensor);
		
		assertEquals(3, controllers.size());
		assertEquals(1, actuators.size());
		assertEquals(1, sensors.size());
		
		assertThrows(IllegalArgumentException.class, ()->service.getThingsByType(Type.valueOf("invalid type")));
	}
	

}
