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
	
	public Quiz(){}
	
	public Quiz(String name, boolean completed, String descprition) {
		this.name = name;
		this.completed = completed;
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

	public boolean addQuestion(Question question){
		return theQuestions.add(question);
	}

	public boolean remvoveQuestion(Question question){
		return theQuestions.remove(question);
	}
			
	public boolean completedQuiz(){
		for(Question q: theQuestions){
			if(q.isCompleted() == false){
				completed = false;
				return completed;
			}
		}
		completed = true;
		return completed;
	}
}