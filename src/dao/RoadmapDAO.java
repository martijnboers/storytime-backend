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

public class RoadmapDAO extends DataAccesObject{
	private PreparedStatement statement;

	public RoadmapDAO() throws Exception {
		super();
	}
	
	public List<Roadmap> getAllRoadmaps() throws SQLException {
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();

		try {
			statement = con.prepareStatement("SELECT Roadmap.roadmap_id, Roadmap.name, Roadmap.description, Roadmap.mentor_id, Roadmap.achievement_id FROM Roadmap");
			ResultSet result = statement.executeQuery();
			
			// TODO: Add categories and steps to roadmap
			while (result.next()) {
				Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"),result.getString("roadmapName"), result.getString("roadmapDescription"));
				if (!theRoadmaps.contains(roadmap)) {
					theRoadmaps.add(roadmap);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theRoadmaps;
	}	
	
	public List<Roadmap> getAllRoadmapsByMentor(int mentor_id) throws SQLException {
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();

		try {
			statement = con.prepareStatement(
					"SELECT Roadmap.name AS roadmapName, Roadmap.description AS roadmapDescription, Step.step_id, Step.name as stepName, Step.description as stepDescription, "
					+ "Step.completed,Achievement.achievement_id, Achievement.name as achievementName, Achievement.points"
					+ "FROM Roadmap"
					+ "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id"
					+ "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id"
					+ "WHERE Roadmap.mentor_id = ?;");
			statement.setInt(1, mentor_id);
						
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"),result.getString("roadmapName"), result.getString("roadmapDescription"));
				if (!theRoadmaps.contains(roadmap)) {
					Achievement achievement = new Achievement(result.getInt("achievement_id"), result.getString("achievementName"), result.getDouble("points"));
					roadmap.setAchievement(achievement);
					Step step = new Step(result.getInt("step_id"),result.getString("stepDescription"),result.getString("stepName"),result.getBoolean("completed"));
					roadmap.addStep(step);
					theRoadmaps.add(roadmap);
				} else {
					for (Roadmap r : theRoadmaps){
						Step step = new Step(result.getInt("step_id"),result.getString("stepDescription"),result.getString("stepName"),result.getBoolean("completed"));
						r.getSteps().add(step);
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
	
	public boolean addRoadmap(String name, String description, int mentor_id, int achievement_id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO Roadmap (`roadmap_id`, `name`, `description`, `mentor_id`, `achievement_id`) VALUES (NULL, ?, ?, ?, ?);");
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setInt(3, mentor_id);
			statement.setInt(4, achievement_id);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Adding Roadmap went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	public boolean updateRoadmap(int roadmap_id, String name, String description, int mentor_id, int achievement_id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Roadmap SET `name` = ?, `description` = ?, `mentor_id` = ?, `achievement_id` = ? WHERE Roadmap.roadmap_id = ?;");
			statement.setInt(5, roadmap_id);
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setInt(3, mentor_id);
			statement.setInt(4, achievement_id);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Updating Roadmap went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	public boolean deleteRoadmap(int roadmap_id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Roadmap WHERE Roadmap.roadmap_id = ?");
			statement.setInt(1, roadmap_id);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Roadmap went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
}