package view;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuiz(String input){
		return roadmapController.addRoadmap(input);
	}
	
	@POST
	@Path("/add/child")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuizToChild(String input){
		return roadmapController.addRoadmapHasChild(input);
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuiz(String input){
		return roadmapController.deleteRoadmap(input);
	}
	
	@GET
	@Path("/roadmaps/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizes(){
		return roadmapController.getAllRoadmaps();
	}
	
	@GET
	@Path("/roadmaps/all/category/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByCategory(@PathParam("id") int id){
		CategoryDAO categoryDAO = new CategoryDAO();
		return roadmapController.getAllRoadmapsByCategory(categoryDAO.getCategoryById(id));
	}
	
	@GET
	@Path("/roadmaps/all/mentor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByMentor(@PathParam("id") int id){
		UserDAO userDAO = new UserDAO();
		return roadmapController.getAllRoadmapsByMentor(userDAO.getMentorById(id));
	}
	
	@POST
	@Path("/update/roadmap")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuiz(String input){
		return roadmapController.updateRoadmap(input);
	}
}