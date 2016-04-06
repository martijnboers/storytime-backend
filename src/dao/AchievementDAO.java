package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.roadmap.Achievement;
import model.roadmap.Roadmap;
import model.roadmap.Step;

public class AchievementDAO extends DataAccesObject {
	private PreparedStatement statement;

	public AchievementDAO() throws Exception {
		super();
	}
	
	/**
	 * 
	 * @return List with all the achievements
	 * @throws SQLException
	 */
	public List<Achievement> getAllAchievements() throws SQLException {
		List<Achievement> theAchievements = new ArrayList<Achievement>();

		try {
			statement = con.prepareStatement("SELECT Achievement.achievement_id, Achievement.name, Achievement.points FROM Achievement");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Achievement achievement = new Achievement(result.getInt("achievement_id"), result.getString("name"), result.getDouble("points"));
				if (!theAchievements.contains(achievement)) {
					theAchievements.add(achievement);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theAchievements;
	}
	
	/**
	 * 
	 * @param child_id
	 * @return List with all the possible achievements of a child
	 * @throws SQLException
	 */
	public List<Achievement> getAllAchievementsByChild(int child_id) throws SQLException {
		List<Achievement> theAchievements = new ArrayList<Achievement>();

		try {
			statement = con.prepareStatement("SELECT Achievement.achievement_id, Achievement.name, Achievement.points"
					+ "FROM Roadmap"
					+ "JOIN Mentor ON Roadmap.mentor_id = Mentor.mentor_id"
					+ "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id"
					+ "JOIN Child ON Child.mentor_id = Mentor.mentor_id"
					+ "WHERE Child.child_id = ?;");
			statement.setInt(1, child_id);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Achievement achievement = new Achievement(result.getShort("achievement_id"), result.getString("name"), result.getDouble("points"));
				theAchievements.add(achievement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theAchievements;
	}

	/**
	 * 
	 * @param child_id
	 * @return List with all the completed achievements of a child
	 * @throws SQLException
	 */
	public List<Achievement> getAllCompletedAchievementsByChild(int child_id) throws SQLException {
		List<Achievement> theAchievements = new ArrayList<Achievement>();

		try {
			statement = con.prepareStatement("SELECT Roadmap.name AS roadmapName, Roadmap.description AS roadmapDescription, Step.step_id, Step.order_id as orderID, Step.name AS stepName, Step.description AS stepDescription, Step_has_Child.completed, Achievement.achievement_id, Achievement.name AS achievementName, Achievement.points"
							+ "FROM Roadmap"
							+ "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id"
							+ "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id"
							+ "JOIN Step_has_Child ON Step.step_id = Step_has_Child.step_id"
							+ "WHERE Step_has_Child.child_id = ?");
			statement.setInt(1, child_id);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Achievement achievement = new Achievement(result.getInt("achievement_id"), result.getString("achievementName"), result.getDouble("points"));
				Roadmap roadmap = new Roadmap(result.getShort("roadmap_id"), result.getString("roadmapName"), result.getString("roadmapDescription"));
				roadmap.setAchievement(achievement);

				if (!theAchievements.contains(achievement)) {
					Step step = new Step(result.getInt("step_id"), result.getInt("orderID"), result.getString("stepName"), result.getString("stepDescription"), result.getBoolean("completed"));
					roadmap.addStep(step);
					
					if(roadmap.isCompleted()) {
						theAchievements.add(achievement);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theAchievements;
	}
	
	/**
	 * 
	 * @param name
	 * @param points
	 * @return True is a achievement is added, false is something failed.
	 * @throws SQLException
	 */
	public boolean addAchievement(String name, double points) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO Achievement (`achievement_id` , `name` , `points`) VALUES (NULL, ?, ?);");
			statement.setString(1, name);
			statement.setDouble(2, points);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Adding Achievement went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	/**
	 * 
	 * @param achievement_id
	 * @param name
	 * @param points
	 * @return True is a achievement is updated, false is something failed.
	 * @throws SQLException
	 */
	public boolean updateAchievement(int achievement_id, String name, double points) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Achievement SET name = ?, points = ? WHERE Achievement.achievement_id = ?;");
			statement.setInt(3, achievement_id);
			statement.setString(1, name);
			statement.setDouble(2, points);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Updating Achievement went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	/**
	 * 
	 * @param achievement_id
	 * @return True is a achievement is deleted, false is something failed.
	 * @throws SQLException
	 */
	public boolean deleteAchievement(int achievement_id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Achievement WHERE Achievement.achievement_id = ?");
			statement.setInt(1, achievement_id);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Achievement went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
}