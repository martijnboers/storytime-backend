package quiz;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.QuizDAO;
import dao.UserDAO;
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
	private QuizDAO quizDAO = new QuizDAO();
	private UserDAO userDAO = new UserDAO();
	
	@Before
	public void initObjects(){
		quiz = new Quiz("testJUnit","Een omschrijving voor dit");
		child = new Child();
		child.setChildId(userDAO.getLatestIdChild());
		mentor = new Mentor("email@email.com", "username", "password", "picture", "naam");
		mentor.setMentorId(userDAO.getLatestIdMentor());
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
	public void testGetAllQuizes(){		
		List<Quiz> theQuizes = quizDAO.getAllQuizes();
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);
	}
	
	@Test
	public void testGetAllQuizesByCategory(){		
		List<Quiz> theQuizes = quizDAO.getAllQuizesByCategory(1);
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);
	}
	
	@Test
	public void testGetAllQuizesByMentor(){		
		List<Quiz> theQuizes = quizDAO.getAllQuizesByMentor(1);
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);	}
	
	@Test
	public void testGetAllQuizesByChild(){		
		List<Quiz> theQuizes = quizDAO.getAllQuizesByChild(child.getChildId());
		assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);	}	
	
	@Test
	public void testAddQuiz(){		
		assertTrue(quizDAO.addQuiz(quiz, mentor.getMentorId()));	}
	
	@Test
	public void testAddQuizToChild() {
		assertTrue(quizDAO.addQuizToChild(1,child.getChildId()));
	}
	
	@Test
	public void testUpdateQuiz() {
		quiz.setQuizId(1);
		question.setQuestionId(1);
		answer.setAnswerId(1);
		quiz.setDescription("Het werk nu super goed");
		assertTrue(quizDAO.updateQuiz(quiz));
	}
	
	@Test
	public void testxDeleteQuizFromChild(){
		assertTrue(quizDAO.deleteQuizFromChild(child.getChildId(), 1));
	}
	
	@Test
	public void testzDeleteQuiz(){
		assertTrue(quizDAO.deleteQuiz(quizDAO.getLatestIdQuestion()));
	}
}