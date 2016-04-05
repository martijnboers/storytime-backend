package users;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.QuizDAO;
import model.category.Category;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;
import model.user.Mentor;

public class TestQuizDAONoTravis {
	private Quiz quiz;
	private Mentor mentor;
	@Test
	public void testGetAllQuizes() throws Exception{		
			QuizDAO quizDAO = new QuizDAO();
			List<Quiz> theQuizes = quizDAO.getAllQuizes();
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
			List<Quiz> theQuizes = quizDAO.getAllQuizesByChild(1);
			assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);	}
	
	@Before
	public void initObjects(){
		quiz = new Quiz("testJUnit","Een omschrijving voor dit");
		
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
		Question question = new Question("Werkt dit JUnit?");
		List<Answer> theAnswers= new ArrayList<Answer>();
		
		Answer answer = new Answer("Antwoord", true);
		theAnswers.add(answer);
		question.setTheAnswers(theAnswers);
		theQuestions.add(question);
		
		quiz.setTheQuestions(theQuestions);
				
	}
	
	
	@Test
	public void testAddQuizd() throws Exception{		
			QuizDAO quizDAO = new QuizDAO();			
			assertTrue(quizDAO.addQuiz(quiz, mentor.getId()));	}
}