package model.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private String question;
	private boolean completed;
	private List<Answer> theAnswers = new ArrayList<Answer>();
	
	public Question(){};	
	
	public Question(String question, boolean completed) {
		super();
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
	/*
	public boolean addAnswer(Answer answer){
		return theAnswers.add(answer);
	} 
	
	public boolean removeAnswer(Answer answer){
		return theAnswers.remove(answer);
	}*/
}
