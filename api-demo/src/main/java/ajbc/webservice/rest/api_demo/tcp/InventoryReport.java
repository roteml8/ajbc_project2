package ajbc.webservice.rest.api_demo.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.models.Device;
import ajbc.webservice.rest.api_demo.models.IOTThing;
import ajbc.webservice.rest.api_demo.models.Type;

public class InventoryReport {
	
	private Socket clientSocket;
	private IOTThing iotThing;
	private final static String SERVER_NAME = "localhost";
	private final static int SERVER_PORT = 9090;
	
	
	public InventoryReport(IOTThing iotThing) {
		this.iotThing = iotThing;
	}


	public IOTThing getIotThing() {
		return iotThing;
	}
	
	public void transmitReportsPeriodically() throws IOException
	{
		Gson gson = new Gson();
		String thingJson = gson.toJson(iotThing);
		
		PrintWriter writer = null;
		BufferedReader bufferReader = null;
		try {
			clientSocket = new Socket(SERVER_NAME, SERVER_PORT);
			System.out.println("\nConnected to server");

			// sending thing data
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			writer.println(thingJson);
			
			//create reader
			bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//reading data
			String line = bufferReader.readLine();
			System.out.println("server says: "+line);
			
			
			

		} catch (UnknownHostException e) {
			System.err.println("Server is not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Socket failed");
			e.printStackTrace();
		} finally {
			if (clientSocket != null)
				clientSocket.close();
			if (writer != null)
				writer.close();
			if (bufferReader != null)
				writer.close();
		}

	}
	
	
	public static void main(String[]args) throws IOException
	{
		IOTThing thing = new IOTThing("ClientModel", "Manufacturer#", Type.CONTROLLER);
		List<Device> thingDevices = new ArrayList<>();
		thingDevices.add(new Device("ClientModel","Manufacturer#", Type.SENSOR));
		thingDevices.add(new Device("ClientModel2","Manufacturer#",Type.ACTUATOR));
		thing.setDevices(thingDevices);
		System.out.println("The Client Thing:");
		System.out.println(thing);
		InventoryReport report = new InventoryReport(thing);
		
		while (true)
		{
			thing.simulateInventoryChange();
			report.transmitReportsPeriodically();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
		
}
	
	


