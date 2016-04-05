package controller;

import java.sql.SQLException;

import dao.UserDAO;
import model.user.Mentor;
import model.user.User;

public class UserController {
	UserDAO userDAO;
	
	public UserController() throws Exception {
		userDAO = new UserDAO();
	}
	

	public void registerMentor(Mentor theMentor) {

	}

	public boolean usernameExists(String username) {
		try {
			if (userDAO.getUsername(username) != null) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public byte[] getProfilePicture(User user) throws SQLException{
		return userDAO.getProfilePicture(user);
	}

}
