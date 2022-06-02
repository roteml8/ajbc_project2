package ajbc.webservice.rest.api_demo.tcp;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MultiThreadedServerRunner implements ServletContextListener {

	InventoryServer server;
	
	public void contextInitialized(ServletContextEvent event) {
		 server = new InventoryServer();
		 server.run();
	}

	
	public void contextDestroyed(ServletContextEvent event) {
		server.kill();
	}
}
