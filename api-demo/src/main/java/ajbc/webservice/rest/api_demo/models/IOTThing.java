package ajbc.webservice.rest.api_demo.models;

import java.util.List;

public class IOTThing extends Hardware {

	private List<Device> devices;
	private final int MIN_RANGE = 10;
	private final int MAX_RANGE = 25;
	private final String DEFAULT_MODEL = "DefModel";
	private final String DEFAULT_MANUFACTURER = "DefManufacturer";
	
	public IOTThing(String model, String manufacturer, Type type) {
		super(model, manufacturer, type);
	}


	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	
	public void simulateInventoryChange()
	{
		if (!devices.isEmpty()) // remove random device
		{
			int numDevices = devices.size();
			int randomIndex = (int) (Math.random()*numDevices);	
			devices.remove(randomIndex);
		}
		
		// add random device
		int randomNumber = (int) (Math.random()*MAX_RANGE + MIN_RANGE);
		
		String randomModel = DEFAULT_MODEL+randomNumber;
		String randomManu = DEFAULT_MANUFACTURER+randomNumber;
		int numTypes = Type.values().length;
		Type randomType = Type.values()[randomNumber%numTypes];
		
		devices.add(new Device(randomModel, randomManu, randomType));
		
		devices.forEach(d->d.simulateReading()); // simulate reading of each device
	
	}


	@Override
	public String toString() {
		String desc = "IOTThing: ID="+getID() + ", model=" + getModel()
		+ ", manufacturer=" + getManufacturer() + ", type=" + getType();
		desc += "\nThing's Devices:";
		for (Device d: devices)
			desc += "\n"+d.toString();
		return desc;
				
	}
	
	
}
