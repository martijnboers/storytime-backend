/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
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
     * @api {post} /user/register Registers a user
     *
     * @apiName register
     * @apiGroup User
     * @apiParam {String} email Email adres.
     * @apiParam {String} username Username of user
     * @apiParam {String} password User password
     * @apiParam {String} profilepicture ProfilePicture
     * @apiParam {String} name Fullname of user
     *
     * @apiError SQLException If there is a db error.
     * @apiError UserDuplicate If the user already exist.
     *
     *
     * @apiSuccessExample Success-Response: { MESSAGE: "Succesvol geregistreerd"
     *                    , STATE: "SUCCEEDED" } }
     *
     * @apiErrorExample Error-Response: { MESSAGE:
     *                  "Er is iets fout gegaan met de mentor toevoegen" ,
     *                  STATE: "ERROR" } }
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
        return userController.addMentor(gson.fromJson(input, Mentor.class));
    }

    /**
     * @api {get} /user/info returns a mentor or child object based on token
     *
     * @apiName info
     * @apiGroup User
     * @apiParam {String} token Token for mentor or child object.
     * @apiError SQLException If there is a db error.
     *
     *
     * @apiSuccessExample Success-Response: user info object
     *
     * @apiErrorExample Error-Response: "Er is iets misgegaan met het ophalen van jouw gegevens. Probeer het nog eens"
     *
     * @param input
     * @return
     * @throws UnknownHostException
     */
    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMentor(@HeaderParam("token") String token) throws Exception {
        return userController.getUserInfo(token);

    }

    /**
     * @api {post} /user/login Login function user
     *
     * @apiName login
     * @apiGroup User
     * @apiParam {String} username Username of user
     * @apiParam {String} password User password
     *
     * @apiError SQLException If there is a db error.
     * @apiError CredentialsMisMatch If the credentials are incorrect.
     *
     *
     * @apiSuccessExample Success-Response:
     *						{ MESSAGE: <token>
     *                    , STATE: "SUCCEEDED" } }
     *
     * @apiErrorExample Error-Response: { MESSAGE:
     *                  "Verkeerde inloggegevens" ,
     *                  STATE: "ERROR" } }
     **/
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(String credentials) throws JsonSyntaxException, SQLException {
        return sec.login(gson.fromJson(credentials, Credentials.class));
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public String logout(@HeaderParam("token") String token) throws InvalidTokenException, SQLException {
        return sec.logout(session.getUserFromToken(token));
    }

    @GET
    @Produces("image/png")
    @Path("/profilepic/{id}")
    public byte[] getProfilePicture(@PathParam("id") int id) throws Exception {
        return userController.getProfilePicture(session.getUserFromId(id));
    }
}
