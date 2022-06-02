package ajbc.webservice.rest.api_demo.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InventoryServer extends Thread {
	
	//TODO use scheduled thread pool
	// each client is one iotthing . each client simulates inventory change - num of devices
	// tomcat server: main thread 
	// we need another thread to run the tcp server 

	private ServerSocket serverSocket;
	private final int PORT = 9090;
	private ExecutorService executorService = Executors.newFixedThreadPool(3);
	
	@Override
	public void run() {

		try (ServerSocket serverSocket = new ServerSocket(PORT);) {

			System.out.println("Server started on port " + PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				executorService.execute(new ServerThread(clientSocket));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.println("Failed to start server on port " + PORT);
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args)
	{
		InventoryServer server = new InventoryServer();
		server.run();
	}

}
