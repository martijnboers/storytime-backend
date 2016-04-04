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

package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logging.Level;
import model.user.Credentials;

import java.util.UUID;

public class SessionManagementDAO extends DataAccesObject {
	private PreparedStatement statement;
	private PreparedStatement token;
	private Statement clean;

	/**
	 * Database functions for SessionManagement
	 * 
	 * @throws Exception
	 */
	public SessionManagementDAO() throws Exception {
		super();
	}

	/**
	 * Checks credentials of user and returns a token that can be used to
	 * authenticate future web calls. Removes old auth tokens for user
	 * 
	 * @param cred
	 * @return
	 * @throws SQLException
	 */
	public String Login(Credentials cred) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT user_id FROM User WHERE username=? AND password=?");
			statement.setString(1, cred.getUsername());
			statement.setString(2, cred.getPassword());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int id = result.getInt("user_id");
				String uuid = UUID.randomUUID().toString();

				clean = con.createStatement();
				clean.executeUpdate("DELETE FROM Tokens WHERE userid = " + id);

				token = con.prepareStatement("INSERT INTO Tokens (token, userid) Values (?, ?)");
				token.setString(1, uuid);
				token.setInt(2, id);
				token.executeUpdate();

				return uuid;

			}
		} catch (Exception e) {
			log.out(Level.ERROR, "Login", "Kan niet inloggen");
		} finally {
			statement.close();
			clean.close();
			token.close();
		}
		return null;
	}
}