package view;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.QuizController;

@Path("/quiz")
public class QuizView extends ViewSuper{
	private QuizController quizController = new QuizController();
	
	public QuizView() throws Exception {
		super();
	}
	
	@POST
	@Path("/addQuiz")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuiz(String input){
		return quizController.addQuiz(input);
	}
	
	@POST
	@Path("/addQuizToChild")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuizToChild(String input){
		return quizController.addQuizToChild(input);
	}
	
	@POST
	@Path("/allQuizes")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuiz(String input){
		return quizController.deleteQuiz(input);
	}
	
	@GET
	@Path("/allQuizes")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizes(){
		return quizController.getAllQuizes();
	}
	
	@GET
	@Path("/allQuizes/ByCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByCategory(String input){
		return quizController.getAllQuizesByCategory(input);
	}
	
	@GET
	@Path("/allQuizes/ByMentor")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByMentor(String input){
		return quizController.getAllQuizesByMentor(input);
	}
	
	@POST
	@Path("/updateQuiz")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuiz(String input){
		return quizController.updateQuiz(input);
	}
}