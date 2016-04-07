package users;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.QuizDAO;
import model.category.Category;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;
import model.user.Child;
import model.user.Mentor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestQuizDAONoTravis {
	private Quiz quiz;
	private Question question;
	private Answer answer;
	private Mentor mentor;
	private Child child;
	
	@Before
	public void initObjects(){
		quiz = new Quiz("testJUnit","Een omschrijving voor dit");
		child = new Child();
		child.setId(2);
		mentor = new Mentor("email@email.com", "username", "password", "picture", "naam");
		mentor.setId(1);
		quiz.setMentor(mentor);	
		quiz.setCompleted(false);
		
		List<Category> theCategories = new ArrayList<Category>();
		Category category = new Category("JUnit");
		category.setId(1);
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
		QuizDAO quizDAO = new QuizDAO();
		List<Quiz> theQuizes = quizDAO.getAllQuizes();
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);
	}
	
	@Test
	public void testGetAllQuizesByCategory() throws Exception{		
		QuizDAO quizDAO = new QuizDAO();
		List<Quiz> theQuizes = quizDAO.getAllQuizesByCategory(1);
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);
	}
	
	@Test
	public void testGetAllQuizesByMentor() throws Exception{		
		QuizDAO quizDAO = new QuizDAO();
		List<Quiz> theQuizes = quizDAO.getAllQuizesByMentor(1);
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);	}
	
	@Test
	public void testGetAllQuizesByChild() throws Exception{		
		QuizDAO quizDAO = new QuizDAO();
		List<Quiz> theQuizes = quizDAO.getAllQuizesByChild(child.getId());
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);	}	
	
	@Test
	public void testAddQuiz() throws Exception{		
		QuizDAO quizDAO = new QuizDAO();			
		assertTrue(quizDAO.addQuiz(quiz, mentor.getId()));	}
	
	@Test
	public void testAddQuizToChild() throws Exception{
		QuizDAO quizDAO = new QuizDAO();
		assertTrue(quizDAO.addQuizToChild(1,child.getId()));
	}
	
	@Test
	public void testUpdateQuiz() throws Exception{
		QuizDAO quizDAO = new QuizDAO();
		quiz.setId(1);
		question.setId(1);
		answer.setId(1);
		quiz.setDescription("Het werk nu goed");
		assertTrue(quizDAO.updateQuiz(quiz));
	}
	
	@Test
	public void testxDeleteQuizFromChild() throws Exception{
		QuizDAO quizDAO = new QuizDAO();
		assertTrue(quizDAO.deleteQuizFromChild(child.getId(), 1));}
	
	@Test
	public void testzDeleteQuiz() throws Exception{
		QuizDAO quizDAO = new QuizDAO();
		int quizId = quizDAO.getLatestIdQuestion();
		assertTrue(quizDAO.deleteQuiz(quizId));}
	
}