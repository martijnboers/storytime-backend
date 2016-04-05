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

package controller;

import java.sql.SQLException;

import dao.SessionManagementDAO;
import exceptions.InvalidTokenException;
import model.user.Mentor;
import model.user.User;

public class SessionController {
	private SessionManagementDAO dao;
	
	public SessionController() throws Exception {
		dao = new SessionManagementDAO();
	}

	public Mentor getMentorFromToken(String token) throws SQLException, InvalidTokenException {
		Mentor men = dao.getMentorFromToken(token);
		if (men == null) {
			throw new InvalidTokenException("Mentor niet gevonden of verkeerde token meegegeven");
		}
		
		return men;
	}

	public User getUserFromId(int id) throws SQLException, InvalidTokenException {
		User user = dao.getUserFromId(id);
		if (user == null) {
			throw new InvalidTokenException("User niet gevonden of verkeerde token meegegeven");
		}
		
		return user;
	}
	
	public User getUserFromToken(String token) throws InvalidTokenException, SQLException {
		User user = dao.getUserFromToken(token);
		if (user == null) {
			throw new InvalidTokenException("User niet gevonden of verkeerde token meegegeven");
		}
		
		return user;
	}
}
