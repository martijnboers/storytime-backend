package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logging.Level;
import logging.Logger;
import model.user.User;

public class UserDAO extends DataAccesObject{
	public UserDAO() throws Exception {
		super();
	}

	private PreparedStatement statement;

	public String getUsername(String username) throws SQLException {
		String theUser = null;

		try {
			statement = con.prepareStatement("SELECT * FROM User WHERE username = ?;");
			statement.setString(1, username);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				theUser = result.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theUser;
	}

	public byte[] getProfilePicture(User user) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT profile_picture FROM User WHERE user_id = ?");
			statement.setInt(1, user.getId());

			ResultSet res = statement.executeQuery();
			while (res.next()) {
				return res.getBytes("profile_picture");
			}
		} catch (Exception e) {
			log.out(Level.ERROR, "getProfilePicture", "Error while getting profilepicture from database");
		} finally {
			statement.close();
		}
		return null;
	}
}
