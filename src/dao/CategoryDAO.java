package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.category.Category;

public class CategoryDAO extends DataAccesObject {
	
	private PreparedStatement statement;

	public CategoryDAO() throws Exception {
		super();
	}

	/**
	 * 
	 * @return List with all the categories
	 * @throws SQLException
	 */
	public List<Category> getAllCategories() throws SQLException {
		List<Category> theCategories = new ArrayList<Category>();

		try {
			statement = con.prepareStatement("SELECT Category.category_id, Category.name FROM Category");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Category category = new Category(result.getInt("category_id"), result.getString("name"));
				if (!theCategories.contains(category)) {
					theCategories.add(category);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theCategories;
	}

	/**
	 * 
	 * @param name
	 * @return True is a category is added, false is something failed.
	 * @throws SQLException
	 */
	public boolean addCategory(String name) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO Category (category_id, name) VALUES (NULL, ?);");
			statement.setString(1, name);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Adding Category went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @return True is a category is updated, false is something failed.
	 * @throws SQLException
	 */
	public boolean updateCategory(int id, String name) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Category SET name = ? WHERE Category.category_id = ?;");
			statement.setInt(2, id);
			statement.setString(1, name);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Updating Category went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	/**
	 * 
	 * @param id
	 * @return True is a category is deleted, false is something failed.
	 * @throws SQLException
	 */
	public boolean deleteCategory(int id) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Category WHERE Category.category_id = ?");
			statement.setInt(1, id);
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Category went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
}