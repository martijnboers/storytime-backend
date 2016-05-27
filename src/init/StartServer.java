/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package init;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import logging.Level;
import logging.Logger;

/**
 * 
 * Use this file to start server
 * 
 * @author martijn
 */
public class StartServer {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/");
		Logger mainLogger = Logger.getInstance();
		mainLogger.out(Level.INFORMATIVE, "Main", "Starting server");
		Server server = new Server(8080);
		mainLogger.out(Level.INFORMATIVE, "Main", "Set handler");
		server.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "view");

		server.start();
		server.join();
		mainLogger.out(Level.INFORMATIVE, "Main", "Server started");
	}
}