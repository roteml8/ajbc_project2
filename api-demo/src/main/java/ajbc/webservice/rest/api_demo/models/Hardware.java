package ajbc.webservice.rest.api_demo.models;

import java.util.Objects;
import java.util.UUID;

public class Hardware {

	private final UUID ID;
	private String model;
	private String manufacturer;
	private Type type;
	
	public Hardware(String model, String manufacturer, Type type) {
		this.ID = UUID.randomUUID();
		this.model = model;
		this.manufacturer = manufacturer;
		this.type = type;
	}
	
	public UUID getID() {
		return ID;
	}

	public String getModel() {
		return model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ID=" + ID + ", model=" + model + ", manufacturer=" + manufacturer + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hardware other = (Hardware) obj;
		return Objects.equals(ID, other.ID);
	}
	
	
	
	
}
