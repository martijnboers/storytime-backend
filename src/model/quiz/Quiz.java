package model.quiz;

import java.util.ArrayList;
import java.util.List;
import model.user.Child;
import model.user.Mentor;

public class Quiz {
	
	private String name;
	private boolean completed;
	private String description;
	private Mentor mentor;
	private List<Child> theChilderen = new ArrayList<Child>();
	private List<Question> theQuestions = new ArrayList<Question>();

	public Quiz() {
	}

	public Quiz(String name, String descprition) {
		this.name = name;
		this.description = descprition;
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

	/*
	 * public boolean addQuestion(Question question){ return
	 * theQuestions.add(question); }
	 * 
	 * public boolean remvoveQuestion(Question question){ return
	 * theQuestions.remove(question); }
	 */

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
		result = prime * result + ((mentor == null) ? 0 : mentor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((theChilderen == null) ? 0 : theChilderen.hashCode());
		result = prime * result + ((theQuestions == null) ? 0 : theQuestions.hashCode());
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
		if (theChilderen == null) {
			if (other.theChilderen != null)
				return false;
		} else if (!theChilderen.equals(other.theChilderen))
			return false;
		if (theQuestions == null) {
			if (other.theQuestions != null)
				return false;
		} else if (!theQuestions.equals(other.theQuestions))
			return false;
		return true;
	}
}