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


    public Project createProject(Calendar startTime, Calendar endTime) {
        if(startTime == null || endTime == null) {
            throw new WrongDateException("Missing date(s).");
        } else if(startTime.after(endTime)) {
            throw new WrongDateException("End date is before start date.");
        } else if(startTime.before(getDate())) {
            throw new WrongDateException("Date is in the past.");
        }

        String iD = String.valueOf(getDate().get(Calendar.YEAR));
        iD = iD.substring(2,4);
        iD += project.length;

        Project newProject = new Project(startTime, endTime, iD);

        projects.add(newProject);

        return newProject;

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
