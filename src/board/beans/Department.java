package board.beans;

import java.io.Serializable;

public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String Name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}


}