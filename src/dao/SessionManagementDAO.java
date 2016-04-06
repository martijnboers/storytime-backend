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
import model.user.Child;
import model.user.Credentials;
import model.user.Mentor;
import model.user.User;

import java.util.UUID;

public class SessionManagementDAO extends DataAccesObject {
	private PreparedStatement statement;
	private PreparedStatement token;
	private Statement clean;
	private Statement logger;

	/**
	 * Database functions for SessionManagement
	 * 
	 * @throws Exception
	 */
	public SessionManagementDAO() throws Exception {
		super();
		log.out(Level.INFORMATIVE, "SessionManagementDAO", "init SessionManagementDao");
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
		boolean loggedIn = false;
		int id = 0;
		
		try {
			statement = con.prepareStatement("SELECT user_id FROM User WHERE username=? AND password=?");
			statement.setString(1, cred.getUsername());
			statement.setString(2, org.apache.commons.codec.digest.DigestUtils.sha256Hex(cred.getPassword()));
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				loggedIn = true;
				id = result.getInt("user_id");
				String uuid = UUID.randomUUID().toString();

				clean = con.createStatement();
				clean.executeUpdate("DELETE FROM Tokens WHERE user_id = " + id);

				token = con.prepareStatement("INSERT INTO Tokens (token, user_id) Values (?, ?)");
				token.setString(1, uuid);
				token.setInt(2, id);
				token.executeUpdate();

				return uuid;
			}
			logger = con.createStatement();
			logger.executeUpdate("INSERT INTO Logs (ip, user_id) VALUES ('null', " + id + ")");	
			
		} catch (Exception e) {
			log.out(Level.ERROR, "Login", "Kan niet inloggen");
		} finally {
			try {
				statement.close();
				clean.close();
				token.close();
				logger.close();
				
			} catch (Exception e) {
				log.out(Level.ERROR, "Login", "Can't close database streams");
			}

		}
		return null;
	}

	public User getUserFromId(int id) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT * FROM User WHERE user_id=?");
			statement.setInt(1, id);
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				return new User(results.getInt("user_id"), results.getString("username"), null,
						"/user/profilepic/" + results.getInt("user_id"), results.getString("name")) {
				};
			}
		} catch (Exception e) {
			log.out(Level.ERROR, "getUserFromID", "Can't get user from id");
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				log.out(Level.ERROR, "getUserFromId", "Can't close database streams");
			}
		}
		return null;
	}

	public Mentor getMentorFromToken(String token) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT user_id FROM Tokens WHERE token=?");
			statement.setString(1, token);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				ResultSet results = clean.executeQuery(
						"SELECT * FROM User INNER JOIN Mentor On User.user_id=Mentor.user_id WHERE User.user_id="
								+ result.getInt("user_id"));
				while (results.next()) {
					return new Mentor(results.getInt("user_id"), results.getString("email"),
							result.getString("username"), "", "/account/profilepic/" + results.getInt("user_id"),
							results.getString("name"));
				}
			}
		} catch (Exception e) {
			log.out(Level.ERROR, "GetMentorFromToken", "Can't get mentor from token");
		} finally {
			try {
				statement.close();
				clean.close();
			} catch (Exception e) {
				log.out(Level.ERROR, "getMentofFromeToken", "Can't close database streams");
			}
		}
		return null;
	}

	public User getUserFromToken(String token) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT user_id FROM Tokens WHERE token=?");
			statement.setString(1, token);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				clean = con.createStatement();
				ResultSet results = clean.executeQuery("SELECT * FROM User WHERE user_id=" + result.getInt("user_id"));
				while (results.next()) {
					return new User(results.getInt("user_id"), results.getString("username"), null,
							"/user/profilepic/" + results.getInt("user_id"), results.getString("name")) {
					};
				}
			}
		} catch (Exception e) {
			log.out(Level.ERROR, "GetUserFromToken", "Can't get user from token");
		} finally {
			try {
				statement.close();
				clean.close();
			} catch (Exception e) {
				log.out(Level.ERROR, "getUserFromToken", "Can't close database streams");
			}
		}
		return null;
	}

	public Child getChildFromToken(String token) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT user_id FROM Tokens WHERE token=?");
			statement.setString(1, token);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				clean = con.createStatement();
				ResultSet results = clean.executeQuery(
						"SELECT * FROM User INNER JOIN Child On User.user_id=Child.user_id WHERE User.user_id="
								+ result.getInt("user_id"));
				while (results.next()) {
					return new Child(results.getInt("user_id"), results.getDate("date_of_birth"),
							results.getString("gender"), results.getString("username"), null,
							"/account/profilepic/" + results.getInt("user_id"), results.getString("name"));
				}
			}
		} catch (Exception e) {
			log.out(Level.ERROR, "GetChildFromToken", "Can't get user from token");
		} finally {
			try {
				statement.close();
				clean.close();
			} catch (Exception e) {
				log.out(Level.ERROR, "getChildFromToken", "Can't close database streams");
			}
		}
		return null;
	}
	
	public boolean logout(User user) throws SQLException {
		boolean worked = false;
		try {
			clean = con.createStatement();
			int affectedRows = clean.executeUpdate("DELETE FROM Tokens WHERE user_id = " + user.getId());
			if (affectedRows >= 1) {
				worked = true;
			}
		} catch (Exception e){
			log.out(Level.ERROR, "logout", "Can't log out user");
		} finally {
			clean.close();
		}
		return worked;
	}
}
