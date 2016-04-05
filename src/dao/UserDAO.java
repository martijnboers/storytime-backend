package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;


public class UserDAO {
	protected Logger log = Logger.getGlobal();
	protected Connection con;
	private PreparedStatement statement;

	public String getUsername(String username) throws SQLException {
		String theUser = null;

		try {
			statement = con.prepareStatement("SELECT * FROM User Where username = ?;");
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
}
