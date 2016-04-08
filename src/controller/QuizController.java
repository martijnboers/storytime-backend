package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import dao.QuizDAO;
import model.State;
import model.quiz.Quiz;


public class QuizController {
	QuizDAO quizDAO;
	
	protected Json json = new Json();
	
	public QuizController(){
		try {
			quizDAO = new QuizDAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAllQuizes(){
		Gson gson = new Gson();
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		try {
			theQuizes = quizDAO.getAllQuizes();
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes");
		}
		if(theQuizes != null && !theQuizes.isEmpty()){
			return json.createJson(State.PASSED,gson.toJson(theQuizes));
		}
		return json.createJson(State.ERROR, "Er zijn geen quizes.");
	}
	
	public String getAllQuizesByMentor(String id){
		int mentorId = Integer.valueOf(id);
		System.out.println(mentorId);
		Gson gson = new Gson();
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		
		try {
			theQuizes = quizDAO.getAllQuizesByMentor(mentorId);
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes.");
		}
		if(theQuizes != null && !theQuizes.isEmpty()){
			return json.createJson(State.PASSED,gson.toJson(theQuizes));
		}
		return json.createJson(State.ERROR, "Er zijn geen quizes.");
	}
	
	public String getAllQuizesByChild(String id){
		int childId = Integer.valueOf(id);
		Gson gson = new Gson();
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		
		try {
			theQuizes = quizDAO.getAllQuizesByChild(childId);
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes.");
		}
		if(theQuizes != null && !theQuizes.isEmpty()){
			return json.createJson(State.PASSED,gson.toJson(theQuizes));
		}
		return json.createJson(State.ERROR, "Er zijn geen quizes.");
	}
	
	public String getAllQuizesByCategory(String id){
		int categoryId = Integer.valueOf(id);
		Gson gson = new Gson();
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		
		try {
			theQuizes = quizDAO.getAllQuizesByCategory(categoryId);
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de quizes.");
		}
		if(theQuizes != null && !theQuizes.isEmpty()){
			return json.createJson(State.PASSED,gson.toJson(theQuizes));
		}
		return json.createJson(State.ERROR, "Er zijn geen quizes voor.");
	}
	
	//TODO all add
	public String addQuiz(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		Quiz quiz = gson.fromJson(input, Quiz.class);
		System.out.println(quiz.toString());
		try {
			quizDAO.addQuiz(quiz, quiz.getMentor().getMentorId());
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de Quiz.");
		}
		return json.createJson(State.PASSED, "Quiz is toegevoegd");
	}
	
	public String addQuizToChild(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		
		Quiz quiz = gson.fromJson(input, Quiz.class);
		try {
			quizDAO.addQuizToChild(quiz.getQuizId(), quiz.getMentor().getMentorId());
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de Quiz");
		}
		return json.createJson(State.PASSED, "Quiz is toegevoegd");
	}
	
	//TODO all updates
	
	public String updateQuiz(String input){
		Json json = new Json();
		Gson gson = new Gson();
		
		Quiz quiz = gson.fromJson(input, Quiz.class);
		try {
			quizDAO.updateQuiz(quiz);
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het updaten van de Quiz");
		}
		return json.createJson(State.PASSED, "Quiz is geüpdated");
		
	}
	
	public String deleteQuiz(String id){
		int quizId = Integer.getInteger(id);
		Gson gson = new Gson();
		
		try {
			quizDAO.deleteQuiz(quizId);
		} catch (SQLException e) {
			json.createJson(State.ERROR, "Quiz kon niet verwijderd worden.");
		}
		
		return json.createJson(State.ERROR, "Quiz is verwijderd.");
	}
}
