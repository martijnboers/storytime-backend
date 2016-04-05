package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import model.roadmap.Roadmap;
import model.roadmap.Step;

public class RoadmapDAO {
	protected Logger log = Logger.getGlobal();
	protected Connection con;
	private PreparedStatement statement;

	public Boolean getAllAchievementsByChild(Roadmap roadmap) throws SQLException {
		Boolean completed = false;
		
		try {
			statement = con.prepareStatement("SELECT Step.step_id, Step.name, Step.description, Step.completed"
					+ "FROM  Step"
					+ "WHERE Step.roadmap_id = ?;");
			statement.setInt(1, roadmap.getId());

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Step step = new Step(result.getShort("step_id"), result.getString("name"), result.getString("description"), result.getBoolean("completed"));
				if(!step.isCompleted()) {
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
}