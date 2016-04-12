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

	public CategoryDAO() {
		super();
	}
	
	/**
	 * 
	 * @return List with all the categories
	 */
	public List<Category> getAllCategories() {
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return theCategories;
	}

	/**
	 * @param category_id
	 * @return Category by given id
	 */
	public Category getCategoryById(int category_id) {
		Category category = new Category();
		try {
			statement = con.prepareStatement("SELECT Category.category_id, Category.name FROM Category WHERE Category.category_id = ?");
			statement.setInt(1, category_id);
			ResultSet result = statement.executeQuery();
			result.next();
			category = new Category(result.getInt("category_id"), result.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return category;
	}
	
	/**
	 * 
	 * @param roadmap
	 * @return List with all the categories of a roadmap
	 */
	public List<Category> getCategoriesByRoadmap(Roadmap roadmap) {
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return theCategories;
	}

	/**
	 * 
	 * @param category
	 * @return True if a category is added, false is something failed.
	 */
	public boolean addCategory(Category category) {
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param category
	 * @return True if a category is updated, false is something failed.
	 */
	public boolean updateCategory(Category category) {
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * Calls deleteCategoryHasQuiz and deleteCategoryHasRoadmap to delete the connections of the Category
	 * @param category
	 * @return True if a category is deleted, false is something failed.
	 */
	public boolean deleteCategory(Category category) {
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param category
	 * @return True if a Category_has_Quiz is deleted, false is something failed.
	 */
	private boolean deleteCategoryHasQuiz(Category category) {
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param category
	 * @return True if a Category_has_Roadmap is deleted, false is something failed.
	 */
	private boolean deleteCategoryHasRoadmap(Category category) {
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR, "", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
}