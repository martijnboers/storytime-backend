package model.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.AchievementController;
import controller.UserController;
import dao.AchievementDAO;
import model.roadmap.Achievement;
import model.roadmap.Roadmap;

public class Child extends User {
	
	private Date dateOfBirth;
	private String gender;
	private List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();

	public Child() {
	}

	public Child(int id, Date dateOfBirth, String gender, String username, String password, String profilePicture, String name) {
		super(id, username, password, profilePicture, name);
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Roadmap> getTheRoadmaps() {
		return theRoadmaps;
	}

	public void setTheRoadmaps(List<Roadmap> theRoadmaps) {
		this.theRoadmaps = theRoadmaps;
	}

	public boolean addRoadmap(Roadmap roadmap) {
		return theRoadmaps.add(roadmap);
	}

	public boolean removeRoadmap(Roadmap roadmap) {
		return theRoadmaps.remove(roadmap);
	}
	
	// TODO: Service DAO netjes maken
	public double getAllAchievementPoints() throws SQLException {
		//AchievementController achievementController = new AchievementController();
		
		double totalPoints = 0;
	//	for(Roadmap roapmap : achievementController.getAllCompletedAchievementsByChild(getId())) {
		//	totalPoints += roapmap.getPoints();
	//	}
		
		return totalPoints;
	}

	@Override
	public String toString() {
		return "dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", theRoadmaps="
				+ theRoadmaps + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Child other = (Child) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		return true;
	}
}