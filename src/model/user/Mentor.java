package model.user;

import java.util.ArrayList;
import java.util.List;

import model.quiz.Quiz;
import model.roadmap.Roadmap;

public class Mentor extends User {
	
	private int mentorId;
	private String email;
	private List<Child> theChildren = new ArrayList<Child>();
	private List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
	private List<Quiz> theQuizes = new ArrayList<Quiz>();

	public Mentor() {
	}
	
	public Mentor(String email, String username, String password, String profilePicture, String name) {
		super(username, password, profilePicture, name);
		this.email = email;
	}

	public Mentor(int id, String email, String username, String password, String profilePicture, String name) {
		super(username, password, profilePicture, name);
		this.mentorId = id;
		this.email = email;
	}
	
	public Mentor(int id, String email, int user_id) {
		this.mentorId = id;
	}

	public int getMentorId() {
		return mentorId;
	}

	public void setMentorId(int id) {
		this.mentorId = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Child> getTheChildren() {
		return theChildren;
	}

	public void setTheChildren(List<Child> theChildren) {
		this.theChildren = theChildren;
	}

	public boolean addChild(Child child) {
		return theChildren.add(child);
	}

	public boolean remvoveChild(Child child) {
		return theChildren.remove(child);
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
	
	public List<Quiz> getTheQuizes() {
		return theQuizes;
	}

	public void setTheQuizes(List<Quiz> theQuizes) {
		this.theQuizes = theQuizes;
	}
	
	public boolean addQuiz(Quiz quiz) {
		return theQuizes.add(quiz);
	}

	public boolean removeQuiz(Quiz quiz) {
		return theQuizes.remove(quiz);
	}

	@Override
	public String toString() {
		return "Mentor [mentorId=" + mentorId + ", email=" + email + ", theChildren=" + theChildren + ", theRoadmaps="
				+ theRoadmaps + ", theQuizes=" + theQuizes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((theChildren == null) ? 0 : theChildren.hashCode());
		result = prime * result + ((theQuizes == null) ? 0 : theQuizes.hashCode());
		result = prime * result + ((theRoadmaps == null) ? 0 : theRoadmaps.hashCode());
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
		Mentor other = (Mentor) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (theChildren == null) {
			if (other.theChildren != null)
				return false;
		} else if (!theChildren.equals(other.theChildren))
			return false;
		if (theQuizes == null) {
			if (other.theQuizes != null)
				return false;
		} else if (!theQuizes.equals(other.theQuizes))
			return false;
		if (theRoadmaps == null) {
			if (other.theRoadmaps != null)
				return false;
		} else if (!theRoadmaps.equals(other.theRoadmaps))
			return false;
		return true;
	}
}