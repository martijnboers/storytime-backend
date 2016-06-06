package controller;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import dao.QuizDAO;
import model.system.State;
import model.quiz.Quiz;


public class QuizController {
	QuizDAO quizDAO;
	
	protected Json json = new Json();
	
	public QuizController(){
			quizDAO = new QuizDAO();
	}
	
	public String getAllQuizes(){
		Gson gson = new Gson();
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		theQuizes = quizDAO.getAllQuizes();
			
		if(theQuizes != null && !theQuizes.isEmpty()){
			return gson.toJson(theQuizes);
		}
		return json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes");
	}
	
	public String getAllQuizesByMentor(int mentorId){
		Gson gson = new Gson();
		
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		theQuizes = quizDAO.getAllQuizesByMentor(mentorId);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes.");
		
		if(theQuizes != null && !theQuizes.isEmpty()){
			return gson.toJson(theQuizes);
		}
		return json.createJson(State.ERROR, "Er zijn geen quizes.");
	}
	
	public String getAllQuizesByChild(int childId){
		Gson gson = new Gson();
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		
		theQuizes = quizDAO.getAllQuizesByChild(childId);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes.");
		if(theQuizes != null && !theQuizes.isEmpty()){
			return gson.toJson(theQuizes);
		}
		return json.createJson(State.ERROR, "Er zijn geen quizes.");
	}
	
	public String getAllQuizesByCategory(int categoryId){
		Gson gson = new Gson();
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		
		theQuizes = quizDAO.getAllQuizesByCategory(categoryId);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes.");
		if(theQuizes != null && !theQuizes.isEmpty()){
			return gson.toJson(theQuizes);
		}
		return json.createJson(State.ERROR, "Er zijn geen quizes voor.");
	}
	
	public String addQuiz(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		Quiz quiz = gson.fromJson(input, Quiz.class);

		quizDAO.addQuiz(quiz, quiz.getMentor().getMentorId());
		json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de Quiz.");
		
		return json.createJson(State.PASSED, "Quiz is toegevoegd");
	}
	
	public String addQuizToChild(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		
		Quiz quiz = gson.fromJson(input, Quiz.class);
		quizDAO.addQuizToChild(quiz.getQuizId(), quiz.getMentor().getMentorId());
		json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de Quiz");
		
		return json.createJson(State.PASSED, "Quiz is toegevoegd");
	}
	
	public String updateQuiz(String input){
		Json json = new Json();
		Gson gson = new Gson();
		
		Quiz quiz = gson.fromJson(input, Quiz.class);
		
		if(quizDAO.updateQuiz(quiz)){
			return json.createJson(State.PASSED, "Quiz is ge�pdated");
		}
		return json.createJson(State.ERROR, "Er is iets fout gegaan met het updaten van de Quiz");	
	}
	
	public String deleteQuiz(String id){
		int quizId = Integer.getInteger(id);
		
		if(quizDAO.deleteQuiz(quizId)){
			return json.createJson(State.PASSED, "Quiz is verwijderd.");			
		}
		return json.createJson(State.ERROR, "Quiz kon niet verwijderd worden.");
	}
}