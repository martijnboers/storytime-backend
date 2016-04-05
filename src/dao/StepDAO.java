package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.roadmap.Step;

public class StepDAO extends DataAccesObject {
	
	private PreparedStatement statement;

	public StepDAO() throws Exception {
		super();
	}

	
	// TODO: Hoe met Step class nou in java? met Completed boolean of niet?
	
	// TODO: getStepById
	// TODO: getAllSteps
	// TODO: addStep
	// TODO: updateStep
	// TODO: deleteStep
	
	public Step getStepById(int step_id) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT Step.step_id, Step.name, Step.description, Step.roadmap_id"
					+ "FROM Step"
					+ "WHERE Step.step_id = ?");
			statement.setInt(1, step_id);
			
			ResultSet result = statement.executeQuery();
			while (result.next()) {
		//		return new Step(result.getInt("step_id"), result.getString("name"), result.getString("description"), result.getInt("roadmap_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return null;
	}
	
	
	/*
	public List<Step> getAllSteps() throws SQLException {
		List<Step> theSteps = new ArrayList<Step>();

		try {
			statement = con.prepareStatement("SELECT Step.step_id, Step.name, Step.description, Step.roadmap_id FROM Step");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Step Step = new Step(result.getInt("step_id"), result.getString("name"), result.getString("description"), result.getInt("roadmap_id"));
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

	
	public boolean addStep(String name) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO Step (Step_id, name) VALUES (NULL, ?);");
			statement.setString(1, name);
			
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
	
	public boolean updateStep(int id, String name) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Step SET name = ? WHERE Step.Step_id = ?;");
			statement.setInt(2, id);
			statement.setString(1, name);
			
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
	
	public boolean deleteStep(int id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Step WHERE Step.Step_id = ?");
			statement.setInt(1, id);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Step went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	*/
}