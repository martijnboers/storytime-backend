package model.roadmap;

import java.util.ArrayList;
import java.util.List;

import model.category.Category;

public class Roadmap {
	private String name;
	private boolean completed;
	private String description;
	private Achievement achievement;
	private List<Category> categories = new ArrayList<Category>();
	private List<Step> steps = new ArrayList<Step>();
	
	public Roadmap () {}
	
	public Roadmap(String nm, boolean com, String description, Achievement ach) {
		this.name = nm;
		this.completed = com;
		this.description = description;
		this.setAchievement(ach);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Achievement getAchievement() {
		return achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}
	
	public void addStep(Step s) {
		steps.add(s);
	}
	
	public void removeStep(Step s) {
		steps.remove(s);
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void addCategory(Category c) {
		categories.add(c);
	}
	
	public void removeCategory(Category c) {
		steps.remove(c);
	}
}
