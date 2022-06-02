package ajbc.webservice.rest.api_demo.beans;

import ajbc.webservice.rest.api_demo.models.Type;
import jakarta.ws.rs.QueryParam;

public class HardwareFilterBean {
	

	@QueryParam("type") Type type;
	@QueryParam("model") String model;
	@QueryParam("manufacturer") String manufacturer;
	
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	

}
