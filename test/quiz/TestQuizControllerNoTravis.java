package quiz;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;

import controller.QuizController;
import dao.QuizDAO;
import model.category.Category;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;
import model.user.Child;
import model.user.Mentor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestQuizControllerNoTravis {
	private Quiz quiz;
	private Question question;
	private Answer answer;
	private Mentor mentor;
	private Child child;
	private Gson gson;
	private QuizController quizController = new QuizController();
	
	@Before
	public void initObjects(){
		quiz = new Quiz("testJUnit","Een omschrijving voor dit");
		child = new Child();
		child.setChildId(2);
		mentor = new Mentor("email@email.com", "username", "password", "picture", "naam");
		mentor.setMentorId(1);
		quiz.setMentor(mentor);	
		quiz.setCompleted(false);
		
		List<Category> theCategories = new ArrayList<Category>();
		Category category = new Category("JUnit");
		category.setCategoryId(1);
		theCategories.add(category);
		quiz.setTheCategories(theCategories);
		
		List<Question> theQuestions = new ArrayList<Question>();
		question = new Question("Dit Werkt ");
		List<Answer> theAnswers= new ArrayList<Answer>();
		
		answer = new Answer("Het Antwoord", true);
		theAnswers.add(answer);
		question.setTheAnswers(theAnswers);
		theQuestions.add(question);
		
		quiz.setTheQuestions(theQuestions);				
	}
	
	@Test
	public void testGetAllQuizes() throws Exception{
		String allQuizes = quizController.getAllQuizes();
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty() && !allQuizes.equals("Er zijn geen quizes."));	
	}
	
	@Test
	public void testGetAllQuizesByCategory() throws Exception{		
		String allQuizes = quizController.getAllQuizesByCategory("1");
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty()&& !allQuizes.equals("Er zijn geen quizes."));	
	}
	
	@Test
	public void testGetAllQuizesByMentor() throws Exception{		
		String allQuizes = quizController.getAllQuizesByChild("1");
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty() && !allQuizes.equals("Er zijn geen quizes."));		
	}
	
	@Test
	public void testGetAllQuizesByChild() throws Exception{		
		String allQuizes = quizController.getAllQuizesByChild("1");
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty() && !allQuizes.equals("Er zijn geen quizes."));	
	}	
	/*
	@Test
	public void testAddQuiz() throws Exception{
		Quiz quiz = new Quiz("testController", "Omschrijving");
		String theQuiz = gson.toJson(quiz).toString();
		System.out.println(theQuiz);
		quizController.addQuiz(theQuiz);		
		assertTrue(quizController.addQuiz(theQuiz).length() > 0 && !theQuiz.isEmpty() && !theQuiz.equals("Er is iets fout gegaan met het toevoegen van de Quiz."));	}
	
	@Test
	public void testAddQuizToChild() throws Exception{
		quizDAO = new QuizDAO();
		assertTrue(quizDAO.addQuizToChild(1,child.getChildId()));
	}
	
	@Test
	public void testUpdateQuiz() throws Exception{
		quizDAO = new QuizDAO();
		quiz.setQuizId(1);
		question.setQuestionId(1);
		answer.setAnswerId(1);
		quiz.setDescription("Het werk nu goed");
		assertTrue(quizDAO.updateQuiz(quiz));
	}
	
	@Test
	public void testxDeleteQuizFromChild() throws Exception{
		quizDAO = new QuizDAO();
		assertTrue(quizDAO.deleteQuizFromChild(child.getChildId(), 1));}
	
	@Test
	public void testzDeleteQuiz() throws Exception{
		quizDAO = new QuizDAO();
		int quizId = quizDAO.getLatestIdQuestion();
		assertTrue(quizDAO.deleteQuiz(quizId));} */
	
}