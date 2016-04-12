package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.category.Category;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;

// TODO: Add log messages at queries
public class QuizDAO extends DataAccesObject {
	
	public QuizDAO() {
		super();
	}
	
	private PreparedStatement statement;

	/**
	 * 
	 * @param mentorId
	 * @return A list with all the Quizes of Mentor
	 */
	public List<Quiz> getAllQuizesByMentor(int mentorId) {
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id, Quiz.name, Quiz.description,Question.question_id, Question.question, Answer.answer_id,Answer.answer, Answer.correct "
							+ "FROM Quiz " 
							+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id " 
							+ "WHERE mentor_id = ?;");
			statement.setInt(1, mentorId);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
							result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
										result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return theQuizes;
	}	
	
	/**
	 * 
	 * @param childId
	 * @return A list with all the Quizes of Child
	 */
	public List<Quiz> getAllQuizesByChild(int childId){
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description, Question.question_id, Question.question, Answer.answer_id, Answer.answer, Answer.correct "
							+ "FROM Quiz " 
							+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id "
							+ "JOIN Child_has_Quiz ON Quiz.quiz_id = Child_has_Quiz.quiz_id " 
							+ "WHERE child_id = ?;");
			statement.setInt(1, childId);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
							result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
										result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								question.setCompleted(isQuestionCompleted(childId, question.getQuestionId()));
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return theQuizes;
	}
	
	/**
	 * 
	 * @param childId
	 * @param questionId
	 * @return True if a Question is completed
	 */
	private boolean isQuestionCompleted(int childId, int questionId){
		boolean completed = false;
		try {
			statement.clearBatch();
			statement = con.prepareStatement("SELECT completed FROM Child_has_Question WHERE child_id = ? AND question_id = ?");
			statement.setInt(1, childId);
			statement.setInt(2, questionId);
			
			ResultSet result = statement.executeQuery();
			while(result.next()){completed = result.getBoolean(1);}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return completed;
	}

	/**
	 * 
	 * @param id
	 * @return A list of Quizes of a Category
	 */
	public List<Quiz> getAllQuizesByCategory(int categoryId){
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description, Question.question_id, Question.question, Answer.answer_id, Answer.answer, Answer.correct "
							+ "FROM Quiz " 
							+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id "
							+ "JOIN Category_has_Quiz ON Quiz.quiz_id = Category_has_Quiz.quiz_id "
							+ "WHERE Category_has_Quiz.Category_id = ?;");
			statement.setInt(1, categoryId);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
							result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
										result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return theQuizes;
	}

	/**
	 * 
	 * @return A list with all the Quizes
	 */
	public List<Quiz> getAllQuizes() {
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		try {
			statement = con.prepareStatement(
					"SELECT Quiz.quiz_id,Quiz.name, Quiz.description,Question.question_id, Question.question, Answer.answer_id,Answer.answer, Answer.correct "
							+ "FROM Quiz " 
							+ "JOIN Question ON Quiz.quiz_id = Question.quiz_id "
							+ "JOIN Answer ON Question.question_id = Answer.question_id;");

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Quiz quiz = new Quiz(result.getInt("quiz_id"), result.getString("name"),
						result.getString("description"));
				if (!theQuizes.contains(quiz)) {
					Question question = new Question(result.getInt("question_id"),result.getString("question"));
					Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
							result.getBoolean("correct"));
					question.getTheAnswers().add(answer);
					quiz.getTheQuestions().add(question);
					theQuizes.add(quiz);
				} else {
					for (Quiz q : theQuizes) {
						Question question = new Question(result.getInt("question_id"),result.getString("question"));
						if (q.equals(quiz)) {
							if (!q.getTheQuestions().contains(question)) {
								Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
										result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							} else {
								for (Question qu : q.getTheQuestions()) {
									if (qu.equals(question)) {
										Answer answer = new Answer(result.getInt("answer_id"),result.getString("answer"),
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
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return theQuizes;
	}
	
	/**
	 * 
	 * @param quiz
	 * @param mentorId
	 * @return True when a Quiz is succesfully added
	 */
	public boolean addQuiz(Quiz quiz, int mentorId) {
		boolean succes = false;
		int quizId = 0;
		int questionId = 0;
		try {
			statement = con.prepareStatement("INSERT INTO Quiz (description,mentor_id,name) VALUES(?,?,?);");
			statement.setString(1, quiz.getDescription());
			statement.setInt(2, mentorId);
			statement.setString(3, quiz.getName());		
		
			if(statement.executeUpdate() >= 1) {
				ResultSet generatedKeyQuiz = statement.getGeneratedKeys();
				
				if (null != generatedKeyQuiz && generatedKeyQuiz.next()) {
					quizId = generatedKeyQuiz.getInt(1);
				}
				generatedKeyQuiz.close();
				
				for(Category category : quiz.getTheCategories()){
					statement.clearBatch();
					if(addCategoryHasQuiz(category.getCategoryId(), quizId)){
						succes = true;
					} else {
						return false;
					}
				}
				if(succes = true){
					for(Question question : quiz.getTheQuestions()){
						statement.clearBatch();
						questionId = addQuestion(question, quizId);
						if(questionId != 0){
							for(Answer answer : question.getTheAnswers()){
								statement.clearBatch();
								if(addAnswer(answer, questionId)){
									succes = true;
								} else {
									return false;
								}
							}
						}
					}
				} else {
					return false;
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Coudn't add a new question");
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param categoryId
	 * @param quizId
	 * @return True when a CategoryHasQuiz is succesfully added
	 */
	private boolean addCategoryHasQuiz(int categoryId, int quizId){
		boolean succes  = false;
		try {
			statement = con.prepareStatement("INSERT INTO Category_has_Quiz (category_id,quiz_id) VALUES(?,?)");
			statement.setInt(1, categoryId);
			statement.setInt(2, quizId);

			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param question
	 * @param quizId
	 * @return True when a Question is succesfully added
	 */
	private int addQuestion(Question question, int quizId) {
		int questionId  = 0;
		try {
			statement = con.prepareStatement("INSERT INTO Question (question,quiz_id) VALUES(?,?)");
				statement.setString(1, question.getQuestion());
				statement.setInt(2, quizId);

			if(statement.executeUpdate() >= 1) {
				ResultSet generatedKeyQuestion = statement.getGeneratedKeys();
				if(null != generatedKeyQuestion && generatedKeyQuestion.next()){
					questionId = generatedKeyQuestion.getInt(1);
				}
				generatedKeyQuestion.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return questionId;
	}
	
	/**
	 * 
	 * @param answer
	 * @param questionId
	 * @return True when an Answer is succesfully added
	 */
	private boolean addAnswer(Answer answer, int questionId) {
		boolean succes  = false;
		try {
			statement = con.prepareStatement("INSERT INTO Answer (answer,correct,question_id) VALUES(?,?,?)");
			statement.setString(1, answer.getAnswer());
			statement.setBoolean(2, answer.isCorrect());
			statement.setInt(3, questionId);

			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}	
	
	/**
	 * 
	 * @param quizId
	 * @param childId
	 * @return True when a Quiz is added succesfully to a Child
	 */
	public boolean addQuizToChild(int quizId, int childId){
		boolean succes  = false;
		try {
			statement = con.prepareStatement("INSERT INTO Child_has_Quiz (child_id,quiz_id) VALUES(?,?)");
			statement.setInt(1, childId);
			statement.setInt(2, quizId);

			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param quiz
	 * @return True when a Quiz is succesfully updated
	 */
	public boolean updateQuiz(Quiz quiz){
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Quiz SET `name` = ?, `description` = ? WHERE quiz_id = ?");
			statement.setString(1, quiz.getName());
			statement.setString(2, quiz.getDescription());
			statement.setInt(3, quiz.getQuizId());

			if(statement.executeUpdate() >= 1){
				for(Question question : quiz.getTheQuestions()){
					if(updateQuestion(question)){
						succes = true;				
						for(Answer answer : question.getTheAnswers()){
							if(updateAnswer(answer)){
								succes = true;
							} else { 
								return false;
							}
						}
					} else {
						return false;
					}
				}
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param question
	 * @return True when a Question is succesfully updated
	 */
	private boolean updateQuestion(Question question){
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Question SET question = ? WHERE question_id = ?;");
			statement.setString(1, question.getQuestion());
			statement.setInt(2, question.getQuestionId());

			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param answer
	 * @return True when a Answer is succesfully updated
	 */
	private boolean updateAnswer(Answer answer) {
		boolean succes = false;
		try {
			statement = con.prepareStatement("UPDATE Answer SET answer = ?, correct = ? WHERE answer_id = ?;");
			statement.setString(1, answer.getAnswer());
			statement.setBoolean(2, answer.isCorrect());
			statement.setInt(3,answer.getAnswerId());

			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	/**
	 * 
	 * @param childId
	 * @param quizId
	 * @return True when a Quiz is succesfully deleted from a Child
	 */
	public boolean deleteQuizFromChild(int childId,int quizId){
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Child_has_Quiz WHERE child_id = ? AND quiz_id = ?;");
			statement.setInt(1, childId);
			statement.setInt(2, quizId);
			
			if(statement.executeUpdate() >= 1){succes = true;}
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Coudn't add a new question");
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}	
		return succes;
	}
	
	/**
	 * 
	 * @param quizId
	 * @return True when a Quiz is succesfully deleted
	 */
	public boolean deleteQuiz(int quizId) {
		boolean succes = false;
		try {
			statement = con.prepareStatement("SELECT question_id FROM Question WHERE quiz_id = ?");
			statement.setInt(1, quizId);
			
			ResultSet result = statement.executeQuery();
			result.next();
			int questionId = result.getInt(1);
			
			if(deleteAnswer(questionId) && deleteQuestions(questionId) && deleteCategoryHasQuiz(quizId)){
				statement.clearBatch();
				statement = con.prepareStatement("DELETE FROM Quiz WHERE Quiz.quiz_id = ?");
				statement.setInt(1,quizId);					
				if(statement.executeUpdate() > 0) {
					succes = true;
				} else {
					succes = false; 					
				}
			} else {
				succes = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.out(Level.ERROR, "", "Coudn't add a new question");
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}		
		return succes;
	}
	
	private boolean deleteAnswer(int questionId) {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Answer WHERE Answer.question_id = ?;");
			statement.setInt(1, questionId);
			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	private boolean deleteQuestions(int questionId) {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Question WHERE Question.question_id = ?;");
			statement.setInt(1, questionId);
			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	private boolean deleteCategoryHasQuiz(int quizId) {
		boolean succes = false;
		try {
			statement = con.prepareStatement("DELETE FROM Category_has_Quiz WHERE Category_has_Quiz.quiz_id = ?;");
			statement.setInt(1, quizId);
			if(statement.executeUpdate() >= 1) {
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				log.out(Level.ERROR,"", "Statement isn't closed");
				e.printStackTrace();
			}
		}
		return succes;
	}
	
	// For testing purpose
	public int getLatestIdQuestion(){
		int questionId = 0;
		try {
			statement = con.prepareStatement("SELECT MAX(quiz_id) FROM Quiz");
			ResultSet result = statement.executeQuery();
			result.next();
			questionId = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return questionId;
	}
}