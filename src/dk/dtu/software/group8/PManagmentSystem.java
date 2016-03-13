package dk.dtu.software.group8;

public class PManagmentSystem {
	
	private Employee currentEmployee = null;
	
	public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

	public boolean signIn(String name) {
		this.currentEmployee = new Employee(name);
		return true;
	}
	
}
