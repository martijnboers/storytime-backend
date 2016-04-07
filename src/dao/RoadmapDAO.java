package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.category.Category;
import model.roadmap.Achievement;
import model.roadmap.Roadmap;
import model.roadmap.Step;
import model.user.Mentor;

// Logic and JavaDoc
// TODO: Check if roadmap has an achievement added to it (Add achievement to roadmap)

// Methods:
// TODO: Add roadmap to child
// TODO: Remove roadmap to child
// TODO: getRoadmapsByChild
// TODO: getAllRoadmapsByCategory
public class RoadmapDAO extends DataAccesObject{
	private PreparedStatement statement;

	public RoadmapDAO() throws Exception {
		super();
	}
	
	public List<Roadmap> getAllRoadmaps() throws Exception {
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
		try {
			statement = con.prepareStatement("SELECT Roadmap.roadmap_id, Roadmap.name, Roadmap.description, Roadmap.mentor_id, Roadmap.achievement_id FROM Roadmap");
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"), result.getString("roadmapName"), result.getString("roadmapDescription"));
				if (!theRoadmaps.contains(roadmap)) {
					
					// TODO: DAO via controller
					CategoryDAO categoryDAO = new CategoryDAO();
					for(Category category : categoryDAO.getCategoriesByRoadmap(roadmap)) {
						roadmap.addCategory(category);
					}
					
					for(Step step : getAllStepByRoadmap(roadmap)) {
						roadmap.addStep(step);
					}
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
	
	public List<Roadmap> getAllRoadmapsByMentor(Mentor mentor) throws SQLException {
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
		try {
			statement = con.prepareStatement("SELECT Roadmap.name AS roadmapName, Roadmap.description AS roadmapDescription, Step.step_id, Step.order_id as orderID, Step.name as stepName, Step.description as stepDescription, Step_has_Child.completed ,Achievement.achievement_id, Achievement.name as achievementName, Achievement.points"
					+ "FROM Roadmap"
					+ "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id"
					+ "JOIN Step_has_Child ON Step_has_Child.step_id = Step.step_id"
					+ "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id"
					+ "WHERE Roadmap.mentor_id = ?;");
			statement.setInt(1, mentor.getId());
						
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"),result.getString("roadmapName"), result.getString("roadmapDescription"));
				if (!theRoadmaps.contains(roadmap)) {
					Achievement achievement = new Achievement(result.getInt("achievement_id"), result.getString("achievementName"), result.getDouble("points"));
					roadmap.setAchievement(achievement);
					Step step = new Step(result.getInt("step_id"), result.getInt("orderID"), result.getString("stepName"), result.getString("stepDescription"), result.getBoolean("completed"));
					roadmap.addStep(step);
					theRoadmaps.add(roadmap);
				} else {
					for (Roadmap r : theRoadmaps){
						Step step = new Step(result.getInt("step_id"), result.getInt("orderID"), result.getString("stepName"), result.getString("stepDescription"), result.getBoolean("completed"));
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
	
	public boolean addRoadmap(Roadmap roadmap) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO Roadmap (`roadmap_id`, `name`, `description`, `mentor_id`, `achievement_id`) VALUES (NULL, ?, ?, ?, ?);");
			statement.setString(1, roadmap.getName());
			statement.setString(2, roadmap.getDescription());
			statement.setInt(3, roadmap.getMentor().getId());
			statement.setInt(4, roadmap.getAchievement().getId());
			
			if(statement.execute() == true) {
				for(Step step : roadmap.getSteps()) {
					addStep(step, roadmap);
				}
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
	
	public boolean updateRoadmap(Roadmap roadmap) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Roadmap SET `name` = ?, `description` = ?, `mentor_id` = ?, `achievement_id` = ? WHERE Roadmap.roadmap_id = ?;");
			statement.setInt(5, roadmap.getId());
			statement.setString(1, roadmap.getName());
			statement.setString(2, roadmap.getDescription());
			statement.setInt(3, roadmap.getMentor().getId());
			statement.setInt(4, roadmap.getAchievement().getId());
			
			if(statement.execute() == true) {
				for(Step step : roadmap.getSteps()) {
					updateStep(step);
				}
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
	
	public boolean deleteRoadmap(Roadmap roadmap) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Roadmap WHERE Roadmap.roadmap_id = ?");
			statement.setInt(1, roadmap.getId());
			
			// Delete steps
			for(Step step : roadmap.getSteps()) {
				deleteStep(step);
			}
			
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
	
	/*private List<Step> getStepsByChild(Child child) throws SQLException {
		List<Step> theSteps = new ArrayList<Step>();
		
		try {
			statement = con.prepareStatement("SELECT Step.step_id, Step.order_id, Step.name, Step.description, Step_has_Child.completed"
					+ "FROM Step"
					+ "JOIN Step_has_Child ON Step.step_id = Step_has_Child.step_id"
					+ "WHERE Step.step_id = ? AND Step_has_Child.child_id = ?");
			statement.setInt(1, child.getId());
			
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Step Step = new Step(result.getInt("step_id"), result.getInt("order_id"), result.getString("name"), result.getString("description"));
				if (!theSteps.contains(Step)) {
					theSteps.add(Step);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theSteps;
	}*/
	
	/**
	 * 
	 * @param roadmap_id
	 * @return Returns a list with all the steps of a roadmap.
	 * @throws SQLException
	 */
	private List<Step> getAllStepByRoadmap(Roadmap roadmap) throws SQLException {
		List<Step> theSteps = new ArrayList<Step>();
		
		try {
			statement = con.prepareStatement("SELECT * FROM `Step` WHERE Step.roadmap_id = ?");
			statement.setInt(1, roadmap.getId());
			
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Step Step = new Step(result.getInt("step_id"), result.getInt("order_id"), result.getString("name"), result.getString("description"));
				if (!theSteps.contains(Step)) {
					theSteps.add(Step);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theSteps;
	}
	
	/*
	private List<Step> getAllSteps() throws SQLException {
		List<Step> theSteps = new ArrayList<Step>();

		try {
			statement = con.prepareStatement("SELECT Step.step_id, Step.order_id, Step.name, Step.description"
					+ "FROM Step"
					+ "JOIN Step_has_Child ON Step.step_id = Step_has_Child.step_id");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Step Step = new Step(result.getInt("step_id"), result.getInt("order_id"), result.getString("name"), result.getString("description"));
				if (!theSteps.contains(Step)) {
					theSteps.add(Step);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theSteps;
	}
	*/
	
	private boolean addStep(Step step, Roadmap roadmap) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO `storytime`.`Step` (`step_id`, `order_id`, `name`, `description`, `roadmap_id`) VALUES (NULL, ?, ?, ?, ?);");
			statement.setInt(1, step.getOrderID());
			statement.setString(2, step.getName());
			statement.setString(3, step.getDescription());
			statement.setInt(4, roadmap.getId());
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Adding Step went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	private boolean updateStep(Step step) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE `storytime`.`Step` SET `order_id` = ?, `name` = ?, `description` = ? WHERE `Step`.`step_id` = ?;");
			statement.setInt(5, step.getId());
			statement.setInt(1, step.getOrderID());
			statement.setString(2, step.getName());
			statement.setString(3, step.getDescription());
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Updating Step went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	/**
	 * 
	 * @param id
	 * @return True is a step is deleted, false is something failed.
	 * @throws SQLException
	 */
	private boolean deleteStep(Step step) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM `storytime`.`Roadmap` WHERE `Roadmap`.`roadmap_id` = ?");
			statement.setInt(1, step.getId());
			if(statement.execute() == true) {
				succes = deleteStepHasChild(step);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Step went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	/**
	 * 
	 * @param step
	 * @return True is a step_has_child is deleted, false is something failed.
	 * @throws SQLException
	 */
	private boolean deleteStepHasChild(Step step) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM `storytime`.`Step_has_Child` WHERE `Step_has_Child`.`step_id` = ?");
			statement.setInt(1, step.getId());
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Step_has_Child went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// return all roadmaps of completed acievements...
	/**
	 * 
	 * @param child_id
	 * @return List with all the completed achievements of a child
	 * @throws SQLException
	 */
	/*
	public List<Roadmap> getAllCompletedAchievementsByChild(int child_id) throws SQLException {
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();

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
				Roadmap roadmap = new Roadmap(result.getShort("roadmap_id"), result.getString("roadmapName"), result.getString("roadmapDescription"), achievement);

				if (!theRoadmaps.contains(roadmap)) {
					Step step = new Step(result.getInt("step_id"), result.getInt("orderID"), result.getString("stepName"), result.getString("stepDescription"), result.getBoolean("completed"));
					roadmap.addStep(step);
					
					if(roadmap.isCompleted()) {
						theRoadmaps.add(roadmap);
					}
				} else {
					for (Roadmap r : theRoadmaps) {
						if (r.equals(achievement)) {
							Step step = new Step(result.getInt("step_id"), result.getInt("orderID"), result.getString("stepName"), result.getString("stepDescription"), result.getBoolean("completed"));
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
	}*/
}