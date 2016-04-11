package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.roadmap.Achievement;

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
					+ "JOIN Child_has_Roadmap ON Child.child_id = Child_has_Roadmap.child_id"
					+ "WHERE Roadmap.roadmap_id = Child_has_Roadmap.roadmap_id AND Child.child_id = ?;");
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
	 * @param achievement_id
	 * @return An achievement with the achievement_id that is given
	 * @throws SQLException
	 */
	public Achievement getAchievementsById(int achievement_id) throws SQLException {
		Achievement achievement = new Achievement();
		try {
			statement = con.prepareStatement("SELECT Achievement.achievement_id, Achievement.name, Achievement.points FROM Achievement WHERE Achievement.achievement_id = ?;");
			statement.setInt(1, achievement_id);
			ResultSet result = statement.executeQuery();
			result.next();
			achievement = new Achievement(result.getShort("achievement_id"), result.getString("name"), result.getDouble("points"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return achievement;
	}

	/**
	 * 
	 * @param achievement
	 * @return True if a achievement is added, false is something failed.
	 * @throws SQLException
	 */
	public boolean addAchievement(Achievement achievement) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO Achievement (`achievement_id` , `name` , `points`) VALUES (NULL, ?, ?);");
			statement.setString(1, achievement.getName());
			statement.setDouble(2, achievement.getPoints());
			
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
	 * @param achievement
	 * @return True if a achievement is updated, false is something failed.
	 * @throws SQLException
	 */
	public boolean updateAchievement(Achievement achievement) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Achievement SET name = ?, points = ? WHERE Achievement.achievement_id = ?;");
			statement.setString(1, achievement.getName());
			statement.setDouble(2, achievement.getPoints());
			statement.setInt(3, achievement.getId());
			
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
	 * @param achievement
	 * @return True if a achievement is deleted, false is something failed.
	 * @throws SQLException
	 */
	public boolean deleteAchievement(Achievement achievement) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Achievement WHERE Achievement.achievement_id = ?");
			statement.setInt(1, achievement.getId());
			
			if(statement.execute() == true) {
				succes = resetAchievementInRoadmap(achievement);;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Achievement went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	/**
	 * 
	 * @param achievement
	 * @return True if all achievement_id are deleted, false is something failed.
	 * @throws SQLException
	 */
	private boolean resetAchievementInRoadmap(Achievement achievement) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE `Roadmap` SET `achievement_id` = NULL WHERE `achievement_id` = ?");
			statement.setInt(1, achievement.getId());
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Resetting achievement_id went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
}