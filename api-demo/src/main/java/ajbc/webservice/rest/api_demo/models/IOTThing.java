package ajbc.webservice.rest.api_demo.models;

import java.util.List;

public class IOTThing extends Hardware {

	private List<Device> devices;
	
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
		if (!devices.isEmpty())
		{
			int numDevices = devices.size();
			int randomIndex = (int) (Math.random()*numDevices);	
			devices.remove(randomIndex);
		}
		int randomNumber = (int) (Math.random()*15 + 1);
		String randomModel = "Model"+randomNumber;
		String randomManu = "Manufacturer"+randomNumber;
		Type randomType = Type.values()[randomNumber%Type.values().length];
		devices.add(new Device(randomModel, randomManu, randomType));
		devices.forEach(d->d.simulateReading());
	
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
