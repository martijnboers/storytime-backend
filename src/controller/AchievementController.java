package controller;

import java.sql.SQLException;
import java.util.List;

import dao.AchievementDAO;
import model.roadmap.Achievement;

public class AchievementController {
	AchievementDAO acievementDAO;
	
	public AchievementController() throws Exception {
		acievementDAO = new AchievementDAO();
	}
	
	public List<Achievement> getAllAchievements() throws SQLException {
		return acievementDAO.getAllAchievements();
	}
	
	public List<Achievement> getAllCompletedAchievementsByChild(int child_id) throws SQLException {
		return acievementDAO.getAllCompletedAchievementsByChild(child_id);
	}
}