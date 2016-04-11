package dk.dtu.software.group8;


import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class PManagementSystem {

    private DatabaseManager db;
    private DateServer dateServer;
	private Employee currentEmployee;
    private List<Project> projects;

    public PManagementSystem() {
        dateServer = new DateServer();
        db = new DatabaseManager();
        projects = new LinkedList<>();
    }


    public Project createProject(Calendar start, Calendar end) {
        if(start.after(end)) {
            throw new WrongDateException("End date is before start date.");
        }
        if(start.before(getDate()) && end.before(getDate())) {
            throw new WrongDateException("Both start and end date are in the past.");
        } else if(start.before(getDate())) {
            throw new WrongDateException("Start date is in the past.");
        }

    }

    public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

	public boolean signIn(String name) {
        //Check if user is actually an employee.
        if (Arrays.stream(db.getEmployees()).anyMatch(e -> e.equals(name))) {
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
