package view;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.RoadmapController;
import dao.CategoryDAO;
import dao.UserDAO;

@Path("/roadmap")
public class RoadmapView extends ViewSuper {
	private RoadmapController roadmapController = new RoadmapController();
	
	public RoadmapView() throws Exception {
		super();
	}
	
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuiz(@HeaderParam("token") String token, String input) throws Exception{
		return roadmapController.addRoadmap(token, input);
	}
	
	@POST
	@Path("/add/child")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuizToChild(String input){
		return roadmapController.addRoadmapHasChild(input);
	}
	
	@DELETE
	@Path("/delete")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuiz(String input){
		return roadmapController.deleteRoadmap(input);
	}
	
	@GET
	@Path("/all")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizes(){
		return roadmapController.getAllRoadmaps();
	}
	
	@GET
	@Path("/category/{id}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByCategory(@PathParam("id") int id){
		CategoryDAO categoryDAO = new CategoryDAO();
		return roadmapController.getAllRoadmapsByCategory(categoryDAO.getCategoryById(id));
	}
	
	@GET
	@Path("/mentor/{id}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByMentor(@PathParam("id") int id){
		UserDAO userDAO = new UserDAO();
		return roadmapController.getAllRoadmapsByMentor(userDAO.getMentorById(id));
	}
	
	@POST
	@Path("/update")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuiz(String input){
		return roadmapController.updateRoadmap(input);
	}
}