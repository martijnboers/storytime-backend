package view;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.AchievementController;

@Path("/achievement")
public class AchievementView extends ViewSuper {
	private AchievementController achievementController = new AchievementController();
	
	public AchievementView() throws Exception {
		super();
	}
	
	@POST
	@Path("/add")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuiz(String input){
		return achievementController.addAchievement(input);
	}
	
	@DELETE
	@Path("/delete")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuiz(String input){
		return achievementController.deleteAchievement(input);
	}
	
	@GET
	@Path("/all")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizes(){
		return achievementController.getAllAchievements();
	}
	
	@GET
	@Path("/{id}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByMentor(@PathParam("id") int id){
		return achievementController.getAchievementByid(id);
	}
	
	@GET
	@Path("/child/{id}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByCategory(@PathParam("id") int id){
		return achievementController.getAllAchievementsByChild(id);
	}
	
	@POST
	@Path("/update")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuiz(String input){
		return achievementController.updateAchievement(input);
	}
}