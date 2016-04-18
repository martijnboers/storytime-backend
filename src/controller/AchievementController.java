package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.AchievementDAO;
import model.system.State;
import model.roadmap.Achievement;

public class AchievementController {
	AchievementDAO acievementDAO;

	protected Json json = new Json();
	
	public AchievementController() {
		try {
			acievementDAO = new AchievementDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getAllAchievements() {
		Gson gson = new Gson();
		List<Achievement> theAchievements = new ArrayList<Achievement>();
			acievementDAO.getAllAchievements();
		if(theAchievements != null && !theAchievements.isEmpty()){
			return json.createJson(State.PASSED,gson.toJson(theAchievements));
		}
		return json.createJson(State.ERROR, "Er zijn geen achievements.");
	}
	
	public List<Achievement> getAllAchievementsByChild(int child_id) throws SQLException {
		return acievementDAO.getAllAchievementsByChild(child_id);
	}
	
	public Achievement getAchievementsById(int achievement_id) throws SQLException {
		return acievementDAO.getAchievementsById(achievement_id);
	}
	
	public boolean addAchievement(Achievement achievement) throws SQLException {
		return acievementDAO.addAchievement(achievement);
	}
	
	public boolean updateAchievement(Achievement achievement) throws SQLException {
		return acievementDAO.updateAchievement(achievement);
	}
	
	public boolean deleteAchievement(Achievement achievement) throws SQLException {
		return acievementDAO.deleteAchievement(achievement);
	}
}