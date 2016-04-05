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
	
	public List<Achievement> getAllAchievementsByChild(int child_id) throws SQLException {
		List<Achievement> theAchievements = new ArrayList<Achievement>();

		try {
			statement = con.prepareStatement("SELECT Achievement.achievement_id, Achievement.name, Achievement.points"
					+ "FROM Roadmap JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id"
					+ "JOIN Child_has_Roadmap ON Roadmap.roadmap_id = Child_has_Roadmap.roadmap_id"
					+ "WHERE Child_has_Roadmap.child_id = ?;");
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

	public List<Roadmap> getAllCompletedAchievementsByChild(int child_id) throws SQLException {
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();

		try {
			statement = con.prepareStatement(
					"SELECT Achievement.achievement_id, Achievement.name, Achievement.points, Roadmap.roadmap_id, Roadmap.name, Roadmap.description, Step.step_id, Step.name, Step.description, Step.completed"
							+ "FROM Roadmap JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id"
							+ "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id"
							+ "JOIN Child_has_Roadmap ON Roadmap.roadmap_id = Child_has_Roadmap.roadmap_id"
							+ "WHERE Child_has_Roadmap.child_id = ?;");
			statement.setInt(1, child_id);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Achievement achievement = new Achievement(result.getInt("achievement_id"), result.getString("name"), result.getDouble("points"));
				Roadmap roadmap = new Roadmap(result.getShort("roadmap_id"), result.getString("name"), result.getString("Roadmap.description"), achievement);

				if (!theRoadmaps.contains(roadmap)) {
					Step step = new Step(result.getInt("step_id"), result.getString("name"), result.getString("Step.description"), result.getBoolean("completed"));
					roadmap.addStep(step);
					
					if(roadmap.isCompleted()) {
						theRoadmaps.add(roadmap);
					}
				} else {
					for (Roadmap r : theRoadmaps) {
						if (r.equals(achievement)) {

							Step step = new Step(result.getInt("step_id"), result.getString("name"), result.getString("Step.description"), result.getBoolean("completed"));
							if (!r.getSteps().contains(step)) {
								roadmap.addStep(step);
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theRoadmaps;
	}
	
	public boolean addAchievement(String name, double points) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO  `storytime`.`Achievement` (`achievement_id` , `name` , `points`)	VALUES (NULL ,  ?,  ?);");
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
	
	public boolean updateAchievement(int id, String name, double points) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Achievement SET name = ?, points = ? WHERE Achievement.achievement_id = ?;");
			statement.setInt(3, id);
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
	
	public boolean deleteAchievement(int id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Achievement WHERE Achievement.achievement_id = ?");
			statement.setInt(1, id);
			
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