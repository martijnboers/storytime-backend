package users;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

import dao.QuizDAO;
import model.quiz.Quiz;

public class TestQuizDAONoTravis {

	@Test
	public void testGetAllQuizes() throws Exception{		
			QuizDAO quiz = new QuizDAO();
			List<Quiz> theQuizes = quiz.getAllQuizes();
			assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);
	}
	
	@Test
	public void testGetAllQuizesByMentor() throws Exception{		
			QuizDAO quiz = new QuizDAO();
			List<Quiz> theQuizes = quiz.getAllQuizesByMentor(1);
			assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);	}
	
	@Test
	public void testGetAllQuizesByChild() throws Exception{		
			QuizDAO quiz = new QuizDAO();
			List<Quiz> theQuizes = quiz.getAllQuizesByChild(1);
			assertTrue(!theQuizes.isEmpty() && theQuizes.size() > 0);	}
}