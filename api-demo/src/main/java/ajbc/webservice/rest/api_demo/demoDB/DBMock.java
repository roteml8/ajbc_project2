package ajbc.webservice.rest.api_demo.demoDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import ajbc.webservice.rest.api_demo.models.Device;
import ajbc.webservice.rest.api_demo.models.IOTThing;
import ajbc.webservice.rest.api_demo.models.Type;

public class DBMock {
	
	private static DBMock instance;
	private Map<UUID, IOTThing> iotThingsMap;
	private Map<UUID, Device> devicesMap;
	
	private DBMock() {
		seedIOTThings();
		seedDevices();

	}

	public static DBMock getInstance()
	{
		if (instance == null)
			instance = new DBMock();
		return instance;
	}
	
	private void seedIOTThings() {
		List<Device> deviceList1 = new ArrayList<>(Arrays.asList(new Device("Model1","Manufacturer1",Type.ACTUATOR),
				new Device("Model2","Manufacturer2",Type.SENSOR)));
		List<IOTThing> iotList = Arrays.asList(new IOTThing("ThingModel1","Manufacturer1",Type.CONTROLLER),
				new IOTThing("ThingModel1", "Manufacturer2", Type.CONTROLLER));
		iotList.get(0).setDevices(deviceList1);
		List<Device> deviceList2 = new ArrayList<>(Arrays.asList(new Device("Model3", "Manufacturer3", Type.CONTROLLER)));
		iotList.get(1).setDevices(deviceList2);
		this.iotThingsMap = iotList.stream().collect(Collectors.toMap(IOTThing::getID, Function.identity()));
	}

	private void seedDevices() {
		List<Device> deviceList = new ArrayList<>();
		iotThingsMap.values().stream().forEach(t->deviceList.addAll(t.getDevices()));
		this.devicesMap = deviceList.stream().collect(Collectors.toMap(Device::getID, Function.identity()));
		
	}


	public Map<UUID, IOTThing> getIotThingsMap() {
		return iotThingsMap;
	}


	public Map<UUID, Device> getDevicesMap() {
		return devicesMap;
	}
	
	

}
