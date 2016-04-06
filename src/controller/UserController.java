package controller;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import dao.SessionManagementDAO;
import dao.UserDAO;
import logging.Level;
import logging.Logger;
import model.State;
import model.user.Child;
import model.user.Mentor;
import model.user.User;

public class UserController {
	UserDAO userDAO;
	private Logger logger = Logger.getInstance();
	Json json = new Json();

	public UserController() throws Exception {
		userDAO = new UserDAO();
	}

	public String addMentor(Mentor theMentor) {
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
			logger.out(Level.ERROR, "userexist", "User already exist");
		}
		return false;
	}

	/**
	 * Provides information about the user, wheter this is a Child or Mentor and
	 * the path the UI should point to
	 * 
	 * TODO: Maybe needs more info but can be added any time
	 * 
	 * @param token
	 * @return JSON String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getUserInfo(String token) throws Exception {
		SessionManagementDAO session = new SessionManagementDAO();
		JSONObject userInfo = new JSONObject();
		
		Child c = session.getChildFromToken(token);
		Mentor m = session.getMentorFromToken(token);

		if (c != null) {
			userInfo.put("Type", "Child");
			userInfo.put("Name", c.getName());
			userInfo.put("Username", c.getUsername());
			userInfo.put("Birthday", c.getDateOfBirth());
			userInfo.put("Gender", c.getGender());

			return json.nestedJson(State.PASSED, userInfo);
		} else if (m != null) {
			userInfo.put("Type", "Mentor");
			userInfo.put("Name", m.getName());
			userInfo.put("Username", m.getUsername());
			userInfo.put("Email", m.getEmail());

			return json.nestedJson(State.PASSED, userInfo);
		}

		return json.createJson(State.ERROR,
				"Er is iets misgegaan met het ophalen van jouw gegevens. Probeer het nog eens");
	}

	public byte[] getProfilePicture(User user) throws SQLException {
		return userDAO.getProfilePicture(user);
	}

}
