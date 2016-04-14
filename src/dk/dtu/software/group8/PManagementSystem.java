package dk.dtu.software.group8;


import dk.dtu.software.group8.Exceptions.InvalidEmployeeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

import java.io.IOException;
import java.util.*;

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

    public Project createProject(Calendar startDate, Calendar endDate) throws WrongDateException, NoAccessException {

        String iD = String.valueOf(getDate().get(Calendar.YEAR)); //Get current year
        iD = iD.substring(2,4); //Keep only last 2 digits of year.

        iD += String.format("%04d", projects.size()); //Add a running 4-digit number equal to number of projects.

        Project newProject = new Project(dateServer, startDate, endDate, iD);

        projects.add(newProject);

        return newProject;
    }

    public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

    public Employee getEmployeeFromId(String iD) throws InvalidEmployeeException {
        Optional<Employee> emp = db.getEmployees().stream().filter(e -> e.getId().equals(iD)).findFirst();

        if (emp.isPresent()) {
            return emp.get();
        } else {
            throw new InvalidEmployeeException("No employee with that name is in the system.");
        }
    }

    public boolean signIn(String iD) {
        //TODO: This could be done smarter with the help of getEmployeeFromId. We should look on that. It should probably just throw the same exception.

        Optional<Employee> emp = db.getEmployees().stream().filter(e -> e.getId().equals(iD)).findFirst();

        if (emp.isPresent()) {
            this.currentEmployee = emp.get();
            return true;
        } else {
            return false;
        }

    }

    public Calendar getDate() {
        return dateServer.getCalendar();
    }

    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

    public boolean userLoggedIn() {
        return currentEmployee != null;
    }

    public List<Project> getProjects() {
        return projects;
    }
}
