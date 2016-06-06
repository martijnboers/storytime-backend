package view;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.CategoryController;
import dao.RoadmapDAO;

@Path("/category")
public class CategoryView extends ViewSuper {
	private CategoryController categoryController = new CategoryController();
	
	public CategoryView() throws Exception {
		super();
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addQuiz(String input){
		return categoryController.addCategory(input);
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuiz(String input){
		return categoryController.deleteCategory(input);
	}
	
	@GET
	@Path("/all")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizes(){
		return categoryController.getAllCategories();
	}
	
	@GET
	@Path("/{id}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByMentor(@PathParam("id") int id){
		return categoryController.getCategoryById(id);
	}
	
	@GET
	@Path("/child/{id}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuizesByCategory(@PathParam("id") int id){
		RoadmapDAO roadmapDAO = new RoadmapDAO();
		return categoryController.getAllCategoriesByRoadmap(roadmapDAO.getRoadmapById(id));
	}
	
	@POST
	@Path("/update/")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuiz(String input){
		return categoryController.updateCategory(input);
	}
}