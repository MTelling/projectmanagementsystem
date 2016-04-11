package dk.dtu.software.group8;


import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class PManagementSystem {

    private DatabaseManager db;
    private DateServer dateServer;
	private Employee currentEmployee = null;
    private List<Project> projects;

    public PManagementSystem() {
        dateServer = new DateServer();
        db = new DatabaseManager();
        projects = new LinkedList<>();
    }

    public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

	public boolean signIn(String name) {
        //Check if user is actually an employee.
        if (Arrays.stream(db.getEmployees()).filter(b -> b.equals(name)).count() > 0) {
            this.currentEmployee = new Employee(name);
            return true;
        }

		return false;
	}

    public Calendar getDate() {
        return dateServer.getCalendar();
    }

    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

    public boolean userLoggedIn() {
        if (currentEmployee != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Project> getProjects() {
        return projects;
    }
}
