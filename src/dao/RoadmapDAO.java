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
	
	public List<Roadmap> getAllRoadmapsByMentor(int id) throws SQLException {
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();

		try {
			statement = con.prepareStatement(
					"SELECT Roadmap.name, Roadmap.description, Step.step_id, Step.name, Step.description, "
					+ "Step.completed,Achievement.achievement_id, Achievement.name, Achievement.points"
					+ "FROM Roadmap"
					+ "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id"
					+ "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id"
					+ "WHERE Roadmap.mentor_id = 1;");
			statement.setInt(1, id);
						
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"),result.getString("Roadmap.name"), result.getString("Roadmap.description"));
				if (!theRoadmaps.contains(roadmap)) {
					Achievement achievement = new Achievement(result.getInt("achievement_id"), result.getString("Achievement.name"), result.getDouble("points"));
					roadmap.setAchievement(achievement);
					Step step = new Step(result.getInt("step_id"),result.getString("Step.description"),result.getString("Step.name"),result.getBoolean("completed"));
					roadmap.addStep(step);
					theRoadmaps.add(roadmap);
				} else {
					for (Roadmap r : theRoadmaps){
						Step step = new Step(result.getInt("step_id"),result.getString("Step.description"),result.getString("Step.name"),result.getBoolean("completed"));
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
			statement = con.prepareStatement("INSERT INTO `storytime`.`Roadmap` (`roadmap_id`, `name`, `description`, `mentor_id`, `achievement_id`) VALUES (NULL, ?, ?, ?, ?);");
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
	
	public boolean updateRoadmap(int id, String name, String description, int mentor_id, int achievement_id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Roadmap SET `name` = ?, `description` = ?, `mentor_id` = ?, `achievement_id` = ? WHERE Roadmap.roadmap_id = ?;");
			statement.setInt(5, id);
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
	
	public boolean deleteRoadmap(int id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Roadmap WHERE `Roadmap`.`roadmap_id` = ?");
			statement.setInt(1, id);
			
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