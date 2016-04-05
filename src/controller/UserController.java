package controller;

import java.sql.SQLException;

import dao.UserDAO;
import model.State;
import model.user.Mentor;
import model.user.User;

public class UserController {
	UserDAO userDAO;
	
	public UserController() throws Exception {
		userDAO = new UserDAO();
	}
	
	public String addMentor(Mentor theMentor) {
		Json json = new Json();
		if (userExists(theMentor.getUsername())) {
			return json.createJson(State.ERROR, "User bestaat al");
		}
		try {
			userDAO.addMentor(theMentor);
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met de mentor toevoegen");
		}
		return json.createJson(State.PASSED, "Succesvol geregistreerd");
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
