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
package view;

import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.server.Response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import controller.Json;
import controller.UserController;
import exceptions.InvalidTokenException;
import model.State;
import model.user.Child;
import model.user.Credentials;
import model.user.Mentor;

@Path("/user")
public class UserRequest extends ViewSuper {
	private UserController userController = new UserController();
	
	public UserRequest() throws Exception {
		super();
		
	}

	/**
	 * TODO: register function. @ xml notation everywere
	 * 
	 * @param input
	 * @return
	 * @throws UnknownHostException
	 */
	@POST
	@Consumes("application/json")
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public String register(String input) throws UnknownHostException {
		Mentor theMentor = gson.fromJson(input, Mentor.class);
		if (!userController.usernameExists(theMentor.getUsername())) {
			// Gebruiker bestaat nog niet
		}
		System.out.println("param1 = " + theMentor.getEmail());
		System.out.println("param2 = " + theMentor.getName());
		return json.createJson(State.PASSED, "Succesvol geregistreerd");
	}

	@GET
	@Path("/account")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMentor(@HeaderParam("token") String token ) throws SQLException, InvalidTokenException {
		Child men = session.getChildFromToken(token);
		Mentor m = new Mentor();
		m.setEmail("Plop");
		m.setName("Henk");
		m.setPassword("Henkie123");
		Gson gson = new Gson();
		String json = gson.toJson(m);
//		return json;
		return men.toString();

	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String credentials) throws JsonSyntaxException, SQLException {
		return sec.login(gson.fromJson(credentials, Credentials.class));
	}
	
	@GET
	@Produces("image/png")
	@Path("/profilepic/{id}")
	public byte[] getProfilePicture(@PathParam("id") int id) throws SQLException, InvalidTokenException {
		return userController.getProfilePicture(session.getUserFromId(id));
	}
}
