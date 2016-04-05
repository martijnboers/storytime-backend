package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;

public class QuizDAO extends DataAccesObject {
	public QuizDAO() throws Exception {super();}

	private PreparedStatement statement;

	public List<Quiz> getAllQuizesByMentor(int id) throws SQLException {
		List<Quiz> theQuizes = new ArrayList<Quiz>();

		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id, Quiz.name, Quiz.description, Question.question, Answer.answer, Answer.correct "
					+ "FROM Quiz "
					+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
					+ "JOIN Answer ON Question.question_id = Answer.question_id "
					+ "WHERE mentor_id = ?;");
			statement.setInt(1, id);
						
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"),result.getString("name"), result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getString("question"));
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
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description, Question.question, Answer.answer, Answer.correct "
					+ "FROM Quiz "
					+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
					+ "JOIN Answer ON Question.question_id = Answer.question_id "
					+ "JOIN Child_has_Quiz ON Quiz.quiz_id = Child_has_Quiz.quiz_id "
					+ "WHERE child_id = ?;");
			statement.setInt(1, id);
						
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"),result.getString("name"), result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getString("answer"), 
										!result.getBoolean("correct"));
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
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description, Question.question, Question.completed, Answer.answer, Answer.correct "
					+ "FROM Quiz "
					+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
					+ "JOIN Answer ON Question.question_id = Answer.question_id "
					+ "JOIN Category_has_Quiz ON Quiz.quiz_id = Category_has_Quiz.quiz_id "
					+ "WHERE Category_has_Quiz.Category_id = ?;");
			statement.setInt(1, id);
						
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"),result.getString("name"), result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getString("answer"), 
										!result.getBoolean("correct"));
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
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description, Question.question, Answer.answer, Answer.correct "
					+ "FROM Quiz "
					+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
					+ "JOIN Answer ON Question.question_id = Answer.question_id;");
						
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"),result.getString("name"), result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getString("question"));
					Answer answer = new Answer(result.getString("answer"), result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getString("answer"), 
										!result.getBoolean("correct"));
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
}