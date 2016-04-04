package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;
import model.user.Mentor;

public class QuizDAO {
	protected Logger log = Logger.getGlobal();
	protected Connection con;

	public List<Quiz> getAllQuizesByMentor(Mentor mentor) {
		List<Quiz> theQuizes = new ArrayList<Quiz>();
		
			PreparedStatement statement;
			try {
				statement = con.prepareStatement("SELECT Quiz.name, Quiz.description, Question.question, Question.completed, Answer.answer, Answer.correct"
						+ "FROM Quiz"
						+ "JOIN Question on Quiz.quiz_id = Question.quiz_id"
						+ "JOIN Answer on Question.question_id = Answer.question_id"
						+ "Where mentor_id = ?;");
				statement.setInt(1, mentor.getId());;
				ResultSet result = statement.executeQuery();
				while(result.next()){
					Quiz quiz = new Quiz(result.getString("name"),result.getString("description"));
					if(!theQuizes.contains(quiz)){
						Question question = new Question(result.getString("question"),result.getBoolean("completed"));
						Answer answer = new Answer(result.getString("answer"),result.getBoolean("correct"));
						question.getTheAnswers().add(answer);
						quiz.getTheQuestions().add(question);
						theQuizes.add(quiz);
					}else{
						for(Quiz q : theQuizes){
							if(q.equals(quiz)){
								Question question = new Question(result.getString("question"),result.getBoolean("completed"));
								Answer answer = new Answer(result.getString("answer"),result.getBoolean("correct"));
								question.getTheAnswers().add(answer);
								q.getTheQuestions().add(question);
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		return theQuizes;		
	}
}
