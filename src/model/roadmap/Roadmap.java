package model.roadmap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.category.Category;

public class Roadmap {

	private int id;
	private String name;
	private String description;
	private Achievement achievement;
	private List<Category> categories = new ArrayList<Category>();
	private List<Step> steps = new ArrayList<Step>();

	public Roadmap() {
	}

	public Roadmap(int id, String nm, String description) {
		this.id = id;
		this.name = nm;
		this.description = description;
	}
	
	public Roadmap(int id, String nm, String description, Achievement achievement) {
		this.id = id;
		this.name = nm;
		this.description = description;
		this.setAchievement(achievement);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean addStep(Step s) {
		return steps.add(s);
	}

	public boolean removeStep(Step s) {
		return steps.remove(s);
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

	public boolean addCategory(Category c) {
		return categories.add(c);
	}

	public boolean removeCategory(Category c) {
		return categories.remove(c);
	}
	
	public Boolean isCompleted() throws SQLException {
		Boolean completed = true;
		for(Step step : steps) {
			if(!step.isCompleted()) {
				completed = false;
				break;
			}
		}
		return completed;
	}
	
	public double getPercentageOfCompletion() {
		double totalSteps = 0;
		double totalCompleted = 0;
		for(Step step : steps) {
			totalSteps += 1;
			if(step.isCompleted()) {
				totalCompleted += 1;
			}
		}
		return Math.ceil((totalCompleted/totalSteps)*100);
	}

	@Override
	public String toString() {
		return "Roadmap [id=" + id + ", name=" + name + ", description=" + description + ", achievement=" + achievement
				+ "(" + getPercentageOfCompletion() + "%), categories=" + categories + ", steps=" + steps + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((achievement == null) ? 0 : achievement.hashCode());
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((steps == null) ? 0 : steps.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Roadmap other = (Roadmap) obj;
		if (achievement == null) {
			if (other.achievement != null)
				return false;
		} else if (!achievement.equals(other.achievement))
			return false;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (steps == null) {
			if (other.steps != null)
				return false;
		} else if (!steps.equals(other.steps))
			return false;
		return true;
	}
}