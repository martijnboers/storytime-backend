package view;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuiz(String input){
		return quizController.addQuiz(input);
	}
	
	@POST
	@Path("/add/child")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuizToChild(String input){
		return quizController.addQuizToChild(input);
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuiz(String input){
		return quizController.deleteQuiz(input);
	}
	
	@GET
	@Path("/quizes/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizes(){
		return quizController.getAllQuizes();
	}
	
	@GET
	@Path("/quizes/all/category/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByCategory(@PathParam("id") int id){
		return quizController.getAllQuizesByCategory(id);
	}
	
	@GET
	@Path("/quizes/all/mentor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByMentor(@PathParam("id") int id){
		return quizController.getAllQuizesByMentor(id);
	}
	
	@POST
	@Path("/update/quiz")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuiz(String input){
		return quizController.updateQuiz(input);
	}
}