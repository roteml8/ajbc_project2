package ajbc.webservice.rest.api_demo.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.Gson;

import ajbc.webservice.rest.api_demo.DBService.DBService;
import ajbc.webservice.rest.api_demo.models.IOTThing;

public class ServerThread implements Runnable {

	private Socket clientSocket;
	private DBService dbService;
	
	public ServerThread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		this.dbService = new DBService();
		
	}
	
	@Override
	public void run() {
		try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
							
			System.out.println(
					"Thing is connected " + clientSocket.getInetAddress() + " port " + clientSocket.getPort());
			// reading data
			String line = bufferReader.readLine();
			//System.out.println("Thing says: " + line);
			
			// processing data
			updateDB(line);
			writer.println("processing report done ");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void updateDB(String line)
	{
		Gson gson = new Gson();
		IOTThing parsedThing = gson.fromJson(line, IOTThing.class);
		UUID thingID = parsedThing.getID();
		dbService.updateThing(thingID, parsedThing);
		System.out.println("\nUpdated DB after receiving report: \n");
		dbService.getAllThings().forEach(t->System.out.println(t+"\n-------------------------"));
		System.out.println();
		dbService.getAllDevices().forEach(t->System.out.println(t+"\n-------------------------"));
	}

}
