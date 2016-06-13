package quiz;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import controller.QuizController;
import model.category.Category;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;
import model.user.Child;
import model.user.Mentor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestQuizController {
	private Quiz quiz;
	private Question question;
	private Answer answer;
	private Mentor mentor;
	private Child child;
	private QuizController quizController = new QuizController();

	@Before
	public void initObjects() {
		quiz = new Quiz("EenAarbei", "Een omschrijving voor dit", 1);
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
		List<Answer> theAnswers = new ArrayList<Answer>();

		answer = new Answer("Het Antwoord", true);
		theAnswers.add(answer);
		question.setTheAnswers(theAnswers);
		theQuestions.add(question);

		quiz.setTheQuestions(theQuestions);
	}

	@Test
	public void testGetAllQuizes() {
		String allQuizes = quizController.getAllQuizes();
		System.out.println(allQuizes);
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty() && !allQuizes.equals("Er zijn geen quizes."));
	}

	@Test
	public void testGetAllQuizesByCategory() {
		String allQuizes = quizController.getAllQuizesByCategory(1);
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty() && !allQuizes.equals("Er zijn geen quizes."));
	}

	@Test
	public void testGetAllQuizesByMentor() {
		String allQuizes = quizController.getAllQuizesByChild(1);
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty() && !allQuizes.equals("Er zijn geen quizes."));
	}

	@Test
	public void testGetAllQuizesByChild() {
		String allQuizes = quizController.getAllQuizesByChild(1);
		assertTrue(allQuizes.length() > 0 && !allQuizes.isEmpty() && !allQuizes.equals("Er zijn geen quizes."));
	}

	@Test
	public void testAddQuiz() {

		String theQuiz = "{\r\n\t\"name\": \" controllerTest \",\r\n\t\"completed\": false,\r\n\t\"desription\": \"het werkt!!\",\r\n\t\"name\": \"foo\",\r\n\t\"mentor\": {\r\n\t\t\"name\": \"foo\",\r\n\t\t\"id\": 1,\r\n\t\t\"email\": \"test@test.nl\",\r\n\t\t\"username\": \"mijnUsername\",\r\n\t\t\"password\": \"geheim\",\r\n\t\t\"profilPicture\": null\r\n\t}\r\n}";

		System.out.println(theQuiz);
		 quizController.addQuiz(theQuiz);
		 assertTrue(quizController.addQuiz(theQuiz).length() > 0);
	}
	/*
	 * @Test public void testAddQuizToChild() throws Exception{ quizDAO = new
	 * QuizDAO(); assertTrue(quizDAO.addQuizToChild(1,child.getChildId())); }
	 * 
	 * @Test public void testUpdateQuiz() throws Exception{ quizDAO = new
	 * QuizDAO(); quiz.setQuizId(1); question.setQuestionId(1);
	 * answer.setAnswerId(1); quiz.setDescription("Het werk nu goed");
	 * assertTrue(quizDAO.updateQuiz(quiz)); }
	 * 
	 * @Test public void testxDeleteQuizFromChild() throws Exception{ quizDAO =
	 * new QuizDAO(); assertTrue(quizDAO.deleteQuizFromChild(child.getChildId(),
	 * 1));}
	 * 
	 * @Test public void testzDeleteQuiz() throws Exception{ quizDAO = new
	 * QuizDAO(); int quizId = quizDAO.getLatestIdQuestion();
	 * assertTrue(quizDAO.deleteQuiz(quizId));}
	 */

}