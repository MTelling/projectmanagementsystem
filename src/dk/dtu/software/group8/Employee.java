package dk.dtu.software.group8;

import java.util.List;

public class Employee {
	
	private String name;
	
	public Employee(String name) {
		this.setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
