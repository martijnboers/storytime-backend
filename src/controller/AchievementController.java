package controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.AchievementDAO;
import model.roadmap.Achievement;
import model.system.State;

public class AchievementController {
	AchievementDAO achiemementDAO;
	
	protected Json json = new Json();
	
	public AchievementController(){
			achiemementDAO = new AchievementDAO();
	}
	
	public String getAllAchievements(){
		Gson gson = new Gson();
		List<Achievement> theAchievements = new ArrayList<Achievement>();
		theAchievements = achiemementDAO.getAllAchievements();
			
		if(theAchievements != null && !theAchievements.isEmpty()){
			return gson.toJson(theAchievements);
		}
		return json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de achievements");
	}
	
	public String getAllAchievementsByChild(int child_id){
		Gson gson = new Gson();
		
		List<Achievement> theAchievements = new ArrayList<Achievement>();
		theAchievements = achiemementDAO.getAllAchievementsByChild(child_id);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de achievements.");
		
		if(theAchievements != null && !theAchievements.isEmpty()){
			return gson.toJson(theAchievements);
		}
		return json.createJson(State.ERROR, "Er zijn geen achievements.");
	}
	
	public String getAchievementByid(int achievement_id){
		Gson gson = new Gson();
		
		Achievement theAchievement = achiemementDAO.getAchievementsById(achievement_id);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het ophalen van de achievements.");
		if(theAchievement != null){
			return gson.toJson(theAchievement);
		}
		return json.createJson(State.ERROR, "Er zijn geen achievements.");
	}
	
	public String addAchievement(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		Achievement achievement = gson.fromJson(input, Achievement.class);

		achiemementDAO.addAchievement(achievement);
		json.createJson(State.ERROR, "Er is iets fout gegaan met het toevoegen van de achievement.");
		
		return json.createJson(State.PASSED, "Achievement is toegevoegd");
	}
	
	public String updateAchievement(String input){
		Json json = new Json();
		Gson gson = new Gson();
		
		Achievement achievement = gson.fromJson(input, Achievement.class);
		
		if(achiemementDAO.updateAchievement(achievement)){
			return json.createJson(State.PASSED, "Achievement is ge�pdated");
		}
		return json.createJson(State.ERROR, "Er is iets fout gegaan met het updaten van de achievement");	
	}
	
	public String deleteAchievement(String input) {
		Json json = new Json();
		Gson gson = new Gson();
		Achievement achievement = gson.fromJson(input, Achievement.class);
		
		if(achiemementDAO.deleteAchievement(achievement)) {
			return json.createJson(State.PASSED, "Achievement is verwijderd.");			
		}
		return json.createJson(State.ERROR, "Achievement kon niet verwijderd worden.");
	}
}