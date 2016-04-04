package model.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {
	
	private String question;
	private boolean completed;
	private List<Answer> theAnswers = new ArrayList<Answer>();

	public Question() {
	}

	public Question(String question, boolean completed) {
		this.question = question;
		this.completed = completed;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public List<Answer> getTheAnswers() {
		return theAnswers;
	}

	public void setTheAnswers(List<Answer> theAnswers) {
		this.theAnswers = theAnswers;
	}
	
	@Override
	public String toString() {
		return "Question [question=" + question + ", completed=" + completed + ", theAnswers=" + theAnswers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((theAnswers == null) ? 0 : theAnswers.hashCode());
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
		Question other = (Question) obj;
		if (completed != other.completed)
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (theAnswers == null) {
			if (other.theAnswers != null)
				return false;
		} else if (!theAnswers.equals(other.theAnswers))
			return false;
		return true;
	}
	
	/*
	 * public boolean addAnswer(Answer answer){ return theAnswers.add(answer); }
	 * 
	 * public boolean removeAnswer(Answer answer){ return
	 * theAnswers.remove(answer); }
	 */
}