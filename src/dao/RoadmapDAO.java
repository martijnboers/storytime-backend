package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;
import model.roadmap.Achievement;
import model.roadmap.Roadmap;
import model.roadmap.Step;

public class RoadmapDAO extends DataAccesObject{
	private PreparedStatement statement;

	public RoadmapDAO() throws Exception {
		super();
	}


	public Boolean isCompleted(int roadmap_id) throws SQLException {
		Boolean completed = true;
		
		try {
			statement = con.prepareStatement("SELECT Step.step_id, Step.name, Step.description, Step.completed"
					+ "FROM  Step"
					+ "WHERE Step.roadmap_id = ?;");
			statement.setInt(1, roadmap_id);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Step step = new Step(result.getShort("step_id"), result.getString("name"), result.getString("description"), result.getBoolean("completed"));
				if(!step.isCompleted()) {
					completed = false;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return completed;
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
}
