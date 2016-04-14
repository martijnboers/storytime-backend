package model.roadmap;

public class Step {

	private int id;
	private int orderID;
	private String name;
	private String description;
	private boolean completed;

	public Step() {
	}
	
	public Step(int orderID, String name, String description) {
		this.orderID = orderID;
		this.name = name;
		this.description = description;
	}
	
	public Step(int orderID, String name, String description, boolean completed) {
		this.orderID = orderID;
		this.name = name;
		this.description = description;
		this.completed = completed;
	}
	
	public Step(int id, int orderID, String name, String description) {
		this.id = id;
		this.orderID = orderID;
		this.name = name;
		this.description = description;
	}

	public Step(int id, int orderID, String name, String description, boolean completed) {
		this.id = id;
		this.orderID = orderID;
		this.name = name;
		this.description = description;
		this.completed = completed;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
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

	@Override
	public String toString() {
		return "Step [id=" + id + ", orderID=" + orderID + ", name=" + name + ", description=" + description
				+ ", completed=" + completed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + orderID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (completed != other.completed)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderID != other.orderID)
			return false;
		return true;
	}
}