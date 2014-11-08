package org.warganiser.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.warganiser.server.servlet.GuiceConfig;

import com.google.inject.servlet.GuiceFilter;

public class WarganiserServerLauncher {

	public static void main(String[] args) throws Exception{
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.warganiser");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.getTransaction().commit();
		entityManager.close();
		
        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8180 if it isn't there.
        String webPort = System.getenv("WARGANISER_SERVER_PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8180";
        }

        Server server = new Server(Integer.valueOf(webPort));
        
        
        // Create a servlet context and add the jersey servlet
        ServletContextHandler sch = new ServletContextHandler(server, "/");
        
        // Add our Guice listener that includes our bindings
        sch.addEventListener(new GuiceConfig());

        // Then add GuiceFilter and configure the server to
        // reroute all requests through this filter.
        sch.addFilter(GuiceFilter.class, "/*", null);
        
        // Must add DefaultServlet for embedded Jetty.
        // Failing to do this will cause 404 errors.
        sch.addServlet(DefaultServlet.class, "/");
        
        // Start the server
        server.start();
        // Wait for server to shutdown
        server.join();
    }

}
