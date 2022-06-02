package ajbc.webservice.rest.api_demo.models;

public class Device extends Hardware {

	private double reading;

	public Device(String model, String manufacturer, Type type) {
		super(model, manufacturer, type);
		this.reading = (int) (Math.random()*10 +1);
	}

	public void simulateReading()
	{
		this.reading += 10;
	}

	@Override
	public String toString() {
		return "Device [ID=" + getID() + ", reading=" + reading + ", model=" + getModel()
				+ ", manufacturer=" + getManufacturer() + ", type=" + getType() + "]";
	}
	
	
	
	
	
	
}
