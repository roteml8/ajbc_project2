package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.demoDB.DBMock;
import ajbc.webservice.rest.api_demo.models.IOTThing;

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
	
//	@Test
//	public void testGetThingById()
//	{
//		IOTThing thing = db.getIotThingsMap().keySet().
//	}
	

}
