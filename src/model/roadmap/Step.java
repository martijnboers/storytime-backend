package model.roadmap;

public class Step {
	private String name;
	private String description;
	private boolean completed;
	public Step() {}
	public Step(String name, String description, boolean completed) {
		this.name = name;
		this.description = description;
		this.completed = completed;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
