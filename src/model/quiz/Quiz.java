package model.quiz;

import java.util.ArrayList;
import java.util.List;

import model.category.Category;
import model.user.Child;
import model.user.Mentor;

public class Quiz {
	private int id;
	private String name;
	private boolean completed;
	private String description;
	private Mentor mentor;
	private List<Child> theChilderen = new ArrayList<Child>();
	private List<Question> theQuestions = new ArrayList<Question>();
	private List<Category> theCategories = new ArrayList<Category>();

	public Quiz() {}

	public Quiz(int id, String name, String descprition) {
		this.id = id;
		this.name = name;
		this.description = descprition;
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

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descprition) {
		this.description = descprition;
	};

	public List<Question> getTheQuestions() {
		return theQuestions;
	}

	public void setTheQuestions(List<Question> theQuestions) {
		this.theQuestions = theQuestions;
	}

	public List<Child> getTheChilderen() {
		return theChilderen;
	}

	public void setTheChilderen(List<Child> theChilderen) {
		this.theChilderen = theChilderen;
	}

	public Mentor getMentor() {
		return mentor;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	public List<Category> getTheCategories() {
		return theCategories;
	}

	public void setTheCategories(List<Category> theCategories) {
		this.theCategories = theCategories;
	}

	public boolean completedQuiz() {
		for (Question q : theQuestions) {
			if (q.isCompleted() == false) {
				completed = false;
				return completed;
			}
		}
		completed = true;
		return completed;
	}

	@Override
	public String toString() {
		return "Quiz [name=" + name + ", completed=" + completed + ", description=" + description + ", mentor=" + mentor
				+ ", theChilderen=" + theChilderen + ", theQuestions=" + theQuestions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((mentor == null) ? 0 : mentor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Quiz other = (Quiz) obj;
		if (completed != other.completed)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (mentor == null) {
			if (other.mentor != null)
				return false;
		} else if (!mentor.equals(other.mentor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}