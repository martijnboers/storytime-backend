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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import controller.Json;
import controller.Security;
import model.State;

/**
 * @author martijn
 * 
 * Prints all exceptions in JSON format to user
 *
 */

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception>{

	public Response toResponse(Exception e) {
		Json json = new Json();
		String message = (e.getMessage().equals("")) ? "Missing message, please see stacktrace" : e.getMessage();
		Security security = new Security();
		
		// For debugging purposes:
		e.printStackTrace();
		
		return Response.status(200)
				.entity(json.createJson(State.ERROR, security.escape(message)))
				.type(MediaType.APPLICATION_JSON).
				build();
	}
}
