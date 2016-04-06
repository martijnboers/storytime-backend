package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

import logging.Level;
import model.category.Category;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;

public class QuizDAO extends DataAccesObject {
	
	public QuizDAO() throws Exception {
		super();
	}
	
	private PreparedStatement statement;

	public List<Quiz> getAllQuizesByMentor(int mentorId) throws SQLException {
		List<Quiz> theQuizes = new ArrayList<Quiz>();

		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id, Quiz.name, Quiz.description,Question.question_id, Question.question, Answer.answer, Answer.correct "
							+ "FROM Quiz " + "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id " + "WHERE mentor_id = ?;");
			statement.setInt(1, mentorId);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getString("answer"),
												result.getBoolean("correct"));
										qu.getTheAnswers().add(answer);
									}
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theQuizes;
	}	
	
	public List<Quiz> getAllQuizesByChild(int id) throws SQLException {
		List<Quiz> theQuizes = new ArrayList<Quiz>();

		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description, Question.question_id, Question.question, Answer.answer, Answer.correct "
							+ "FROM Quiz " + "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id "
							+ "JOIN Child_has_Quiz ON Quiz.quiz_id = Child_has_Quiz.quiz_id " + "WHERE child_id = ?;");
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getString("answer"), !result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getString("answer"),
												result.getBoolean("correct"));
										qu.getTheAnswers().add(answer);
									}
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theQuizes;
	}

	public List<Quiz> getAllQuizesByCategorie(int id) throws SQLException {
		List<Quiz> theQuizes = new ArrayList<Quiz>();

		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description, Question.question_id, Question.question, Question.completed, Answer.answer, Answer.correct "
							+ "FROM Quiz " 
							+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id "
							+ "JOIN Category_has_Quiz ON Quiz.quiz_id = Category_has_Quiz.quiz_id "
							+ "WHERE Category_has_Quiz.Category_id = ?;");
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getString("answer"), !result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getString("answer"),
												result.getBoolean("correct"));
										qu.getTheAnswers().add(answer);
									}
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theQuizes;
	}

	public List<Quiz> getAllQuizes() throws SQLException {
		List<Quiz> theQuizes = new ArrayList<Quiz>();

		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description,Question.question_id, Question.question, Answer.answer, Answer.correct "
							+ "FROM Quiz " + "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id;");

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getString("answer"), !result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getString("answer"),
												result.getBoolean("correct"));
										qu.getTheAnswers().add(answer);
									}
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return theQuizes;
	}
	
	public boolean addQuiz(Quiz quiz, int mentorId) throws SQLException{
		boolean succes = false;
		int quizId = 0;
		int questionId = 0;
		try {
			statement = con.prepareStatement("INSERT INTO Quiz "
					+ "(description,mentor_id,name) VALUES(?,?,?);");
			statement.setString(1, quiz.getDescription());
			statement.setInt(2, mentorId);
			statement.setString(3,quiz.getName());		
		
			if(statement.executeUpdate() >= 1) {
						
				ResultSet generatedKeyQuiz = statement.getGeneratedKeys();
				
				if (null != generatedKeyQuiz && generatedKeyQuiz.next()) {
					quizId = generatedKeyQuiz.getInt(1);
				}
				generatedKeyQuiz.close();
				
				for(Category category : quiz.getTheCategories()){
					statement.clearBatch();
					addCategoryHasQuiz(category.getId(), quizId);
				}
				
				for(Question question : quiz.getTheQuestions()){
					statement.clearBatch();
					questionId = addQuestion(question, quizId);
					if(questionId != 0){
						for(Answer answer : question.getTheAnswers()){
							statement.clearBatch();
							if(addAnswer(answer, questionId)){
								succes = true;
							}
						}
					}
				}
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Coudn't add a new question");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	private boolean addCategoryHasQuiz(int categoryId, int quizId) throws SQLException{
		boolean succes  = false;
		try {
			statement = con.prepareStatement("INSERT INTO Category_has_Quiz "
					+ "(category_id,quiz_id) VALUES(?,?)");
			statement.setInt(1,categoryId);
			statement.setInt(2,quizId);

			if(statement.executeUpdate() >= 1) {succes = true;}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return succes;
	}
	
	private int addQuestion(Question question, int quizId) throws SQLException{
		int questionId  = 0;
		try {
			statement = con.prepareStatement("INSERT INTO Question "
					+ "(question,quiz_id) VALUES(?,?)");
				statement.setString(1,question.getQuestion());
				statement.setInt(2,quizId);

			if(statement.executeUpdate() >= 1) {
				ResultSet generatedKeyQuestion = statement.getGeneratedKeys();
				if(null != generatedKeyQuestion && generatedKeyQuestion.next()){
					questionId = generatedKeyQuestion.getInt(1);				}
				generatedKeyQuestion.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return questionId;
	}
	
	private boolean addAnswer(Answer answer, int questionId) throws SQLException{
		boolean succes  = false;
		try {
			statement = con.prepareStatement("INSERT INTO Answer "
					+ "(answer,correct,question_id) VALUES(?,?,?)");
			statement.setString(1,answer.getAnswer());
			statement.setBoolean(2, answer.isCorrect());
			statement.setInt(3,questionId);

			if(statement.executeUpdate() >= 1) {succes = true;}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return succes;
	}	
	
	
	public boolean updateQuiz(Quiz quiz) throws SQLException{
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Quiz SET `name` = ?, `description` = ?, `mentor_id` = ?;");
			statement.setString(1, quiz.getName());
			statement.setString(2, quiz.getDescription());
			statement.setInt(3, quiz.getMentor().getId());

			if(statement.executeUpdate() >= 1) {
				for(Question question : quiz.getTheQuestions()){
					if(updateQuestion(question)){					
						for(Answer answer : question.getTheAnswers()){
							updateAnswer(answer);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Updating Roadmap went wrong");
		} finally {
			statement.close();
		}
		return succes;
	}
	
	private boolean updateQuestion(Question question) throws SQLException{
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Question SET question = ?");
			statement.setString(1, question.getQuestion());

			if(statement.executeUpdate() >= 1) {succes = true;}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return succes;
	}
	
	private boolean updateAnswer(Answer answer) throws SQLException{
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Question SET question = ?, correct = ?");
			statement.setString(1, answer.getAnswer());
			statement.setBoolean(2, answer.isCorrect());

			if(statement.executeUpdate() >= 1) {succes = true;}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return succes;
	}
	
		
	public boolean deleteQuiz(int quizId) throws SQLException{
		boolean succes = false;
		try {
			statement = con.prepareStatement("SELECT question_id "
					+ "FROM Question "
					+ "WHERE quiz_id = ?");
			statement.setInt(1, quizId);
			
			ResultSet result = statement.executeQuery();
			result.next();
			int questionId = result.getInt(1);
			statement.clearBatch();
			
			statement = con.prepareStatement("DELETE FROM Answer "
					+ "WHERE Answer.question_id = ?;");
			statement.setInt(1,questionId);
			
			if(statement.executeUpdate() > 0){
				statement.clearBatch();
				statement = con.prepareStatement("DELETE FROM Question "
						+ "WHERE Question.question_id = ?;");
				statement.setInt(1,questionId);
				if(statement.executeUpdate() > 0){
					statement.clearBatch();
					statement = con.prepareStatement("DELETE FROM Category_has_Quiz "
							+ "WHERE Category_has_Quiz.quiz_id = ?;");
					statement.setInt(1,quizId);
					
					if(statement.executeUpdate() > 0){
						statement.clearBatch();
						statement = con.prepareStatement("DELETE FROM Quiz "  
							+ "WHERE Quiz.quiz_id = ?");
						statement.setInt(1,quizId);					
						if(statement.executeUpdate() > 0){
							succes = true;
						}
					}
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Coudn't add a new question");
		} finally {
			statement.close();
		}		
		return succes;
	}
	
	public boolean addQuizToChild(int quizId, int childId) throws SQLException{
		boolean succes  = false;
		try {
			statement = con.prepareStatement("INSERT INTO Child_has-Quiz "
					+ "(child_id,quiz_id) VALUES(?,?,?)");
			statement.setInt(1,childId);
			statement.setInt(2,quizId);			

			if(statement.executeUpdate() >= 1) {succes = true;}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return succes;
	}
	
	public int getLatestIdQuestion() throws SQLException{
		int questionId = 0;
		try {
			statement = con.prepareStatement("SELECT MAX(quiz_id) FROM Quiz");
			ResultSet result = statement.executeQuery();
			result.next();
			questionId = result.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
		}
		return questionId;
	}
}