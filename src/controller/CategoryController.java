package controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.CategoryDAO;
import model.category.Category;
import model.roadmap.Roadmap;
import model.system.State;

public class CategoryController {
	CategoryDAO categoryDAO;

	protected Json json = new Json();

	public CategoryController() {
		categoryDAO = new CategoryDAO();
	}

	public String getAllCategories() {
		Gson gson = new Gson();
		List<Category> theCategories = new ArrayList<Category>();
		theCategories = categoryDAO.getAllCategories();

		if (theCategories != null && !theCategories.isEmpty()) {
			return gson.toJson(theCategories);
		}
		return json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de categories");
	}

	public String getAllCategoriesByRoadmap(Roadmap roadmap) {
		Gson gson = new Gson();
		
		List<Category> theCategories = new ArrayList<Category>();
		theCategories = categoryDAO.getCategoriesByRoadmap(roadmap);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de categories.");

		if (theCategories != null && !theCategories.isEmpty()) {
			return gson.toJson(theCategories);
		}
		return json.createJson(State.ERROR, "Er zijn geen categories.");
	}

	public String getCategoryById(int category_id) {
		Gson gson = new Gson();

		Category theCategory = categoryDAO.getCategoryById(category_id);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de categories.");
		if (theCategory != null) {
			return json.createJson(State.PASSED, gson.toJson(theCategory));
		}
		return json.createJson(State.ERROR, "Er zijn geen categories.");
	}

	public String addCategory(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		Category category = gson.fromJson(input, Category.class);

		categoryDAO.addCategory(category);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de category.");

		return json.createJson(State.PASSED, "Category is toegevoegd");
	}

	public String updateCategory(String input) {
		Json json = new Json();
		Gson gson = new Gson();

		Category category = gson.fromJson(input, Category.class);

		if (categoryDAO.updateCategory(category)) {
			return json.createJson(State.PASSED, "Category is ge�pdated");
		}
		return json.createJson(State.ERROR, "Er is iets fout gegaan met het updaten van de category");
	}

	public String deleteCategory(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		Category category = gson.fromJson(input, Category.class);

		if (categoryDAO.deleteCategory(category)) {
			return json.createJson(State.PASSED, "Category is verwijderd.");
		}
		return json.createJson(State.ERROR, "Category kon niet verwijderd worden.");
	}
}