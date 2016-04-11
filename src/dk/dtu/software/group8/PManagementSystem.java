package dk.dtu.software.group8;


import java.util.Arrays;

public class PManagementSystem {

    private DatabaseManager db = new DatabaseManager();
	
	private Employee currentEmployee = null;
	
	public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

	public boolean signIn(String name) {
        if (Arrays.stream(db.getEmployees()).anyMatch(b -> b == name)) {
            this.currentEmployee = new Employee(name);
            return true;
        }

		return false;
	}
	
}
