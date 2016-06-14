package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.RoadmapDAO;
import dao.SessionManagementDAO;
import model.system.State;
import model.user.Child;
import model.user.Mentor;
import model.category.Category;
import model.quiz.Quiz;
import model.roadmap.Roadmap;

public class RoadmapController {
	RoadmapDAO roadmapDAO;
	
	protected Json json = new Json();
	
	public RoadmapController(){
			roadmapDAO = new RoadmapDAO();
	}
	
	public String getAllRoadmaps(){
		Gson gson = new Gson();
		
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
		theRoadmaps = roadmapDAO.getAllRoadmaps();
			
		if(theRoadmaps != null && !theRoadmaps.isEmpty()){
			return gson.toJson(theRoadmaps);
		}
		return gson.toJson(theRoadmaps);
	//	return json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de roadmaps");
	}
	
	public String getAllRoadmapsByMentor(Mentor mentor){
		Gson gson = new Gson();
		
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
		theRoadmaps = roadmapDAO.getAllRoadmapsByMentor(mentor);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de roadmaps.");
		
		if(theRoadmaps != null && !theRoadmaps.isEmpty()){
			return gson.toJson(theRoadmaps);
		}
		return json.createJson(State.ERROR, "Er zijn geen roadmaps.");
	}
	
	public String getAllRoadmapssByChild(Child child){
		Gson gson = new Gson();
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
		
		theRoadmaps = roadmapDAO.getAllRoadmapsByChild(child);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de roadmaps.");
		if(theRoadmaps != null && !theRoadmaps.isEmpty()){
			return gson.toJson(theRoadmaps);
		}
		return json.createJson(State.ERROR, "Er zijn geen roadmaps.");
	}
	
	public String getAllRoadmapsByCategory(Category category){
		Gson gson = new Gson();
		List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
		
		theRoadmaps = roadmapDAO.getAllRoadmapsByCategory(category);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de roadmaps.");
		if(theRoadmaps != null && !theRoadmaps.isEmpty()){
			return gson.toJson(theRoadmaps);
		}
		return json.createJson(State.ERROR, "Er zijn geen roadmaps voor deze category.");
	}
	
	public String addRoadmap(String token, String input) throws Exception {
		Json json = new Json();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
		SessionManagementDAO session = new SessionManagementDAO();
		
		Roadmap r = gson.fromJson(input, Roadmap.class);
		Mentor m = session.getMentorFromToken(token);
		r.setMentor(m);
		if (!roadmapDAO.addRoadmap(r)) {
			json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de Roadmap.");
		}
		
		return json.createJson(State.PASSED, "Roadmap is toegevoegd");
	}
	
	public String addRoadmapHasChild(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		
		Roadmap roadmap = gson.fromJson(input, Roadmap.class);
		roadmapDAO.addRoadmapHasChild(roadmap, roadmap.getMentor().getTheChildren().get(roadmap.getMentor().getTheChildren().size()));
		json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de Roadmap");
		
		return json.createJson(State.PASSED, "Roadmap is toegevoegd");
	}
	
	public String updateRoadmap(String input){
		Json json = new Json();
		Gson gson = new Gson();
		
		Roadmap roadmap = gson.fromJson(input, Roadmap.class);
		
		if(roadmapDAO.updateRoadmap(roadmap)){
			return json.createJson(State.PASSED, "Roadmap is ge�pdated");
		}
		return json.createJson(State.ERROR, "Er is iets fout gegaan met het updaten van de Roadmap");	
	}
	
	public String deleteRoadmap(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		Roadmap roadmap = gson.fromJson(input, Roadmap.class);
		
		if(roadmapDAO.deleteRoadmap(roadmap)) {
			return json.createJson(State.PASSED, "Roadmap is verwijderd.");			
		}
		return json.createJson(State.ERROR, "Roadmap kon niet verwijderd worden.");
	}
}