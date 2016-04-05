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
	
	public boolean addMentor(Mentor theMentor) {
		try {
			return userDAO.addMentor(theMentor);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean userExists(String username) {
		try {
			return userDAO.userExists(username);
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
