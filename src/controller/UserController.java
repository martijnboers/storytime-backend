package controller;

import java.sql.SQLException;

import dao.UserDAO;
import model.user.Mentor;

public class UserController {
	UserDAO userDAO = new UserDAO();
	public void registerMentor(Mentor theMentor)
	{
		
	}
	public boolean usernameExists(String username)
	{
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
	

}
