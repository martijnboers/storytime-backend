package model.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.roadmap.Roadmap;

public class Child extends User {

	private int id;
	private Date dateOfBirth;
	private String gender;
	private List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();

	public Child() {
	}

	public Child(int id, Date dateOfBirth, String gender) {
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Child [id=" + id + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", theRoadmaps="
				+ theRoadmaps + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}
}