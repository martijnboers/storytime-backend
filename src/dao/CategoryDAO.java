package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.category.Category;
import model.roadmap.Roadmap;

public class CategoryDAO extends DataAccesObject {
	
	private PreparedStatement statement;

	public CategoryDAO() throws Exception {
		super();
	}
	
	/**
	 * @param category_id
	 * @return Category by given id
	 * @throws SQLException
	 */
	public Category getCategoryById(int category_id) throws SQLException {
		try {
			statement = con.prepareStatement("SELECT Category.category_id, Category.name FROM Category WHERE Category.category_id = ?");
			statement.setInt(1, category_id);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				return new Category(result.getInt("category_id"), result.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return null;
	}
	
	public List<Category> getCategoriesByRoadmap(Roadmap roadmap) throws SQLException {
		List<Category> theCategories = new ArrayList<Category>();
		
		try {
			statement = con.prepareStatement("SELECT * FROM `Category_has_Roadmap` WHERE Category_has_Roadmap.roadmap_id = ?");
			statement.setInt(1, roadmap.getId());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Category category = getCategoryById(result.getInt("cagetory_id"));
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
	public boolean addCategory(Category category) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("INSERT INTO Category (category_id, name) VALUES (NULL, ?);");
			statement.setString(1, category.getName());
			
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
	public boolean updateCategory(Category category) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Category SET name = ? WHERE Category.category_id = ?;");
			statement.setInt(2, category.getCategoryId());
			statement.setString(1, category.getName());
			
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
	
	public boolean deleteCategory(Category category) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Category WHERE Category.category_id = ?");
			statement.setInt(1, category.getCategoryId());
			
			if(statement.execute() == true) {
				succes = deleteCategoryHasQuiz(category) && deleteCategoryHasRoadmap(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting Category went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	private boolean deleteCategoryHasQuiz(Category category) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM `storytime`.`Category_has_Quiz` WHERE `Category_has_Quiz`.`category_id` = ?");
			statement.setInt(1, category.getCategoryId());
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting category_has_quiz went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	private boolean deleteCategoryHasRoadmap(Category category) throws SQLException {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM `storytime`.`Category_has_Roadmap` WHERE `Category_has_Roadmap`.`category_id` = ?");
			statement.setInt(1, category.getCategoryId());
			
			if(statement.execute() == true) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Deleting category_has_roadmap went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
}