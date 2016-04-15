package dk.dtu.software.group8;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.InvalidEmployeeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

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

    public Project createProject(LocalDate startDate, LocalDate endDate) throws WrongDateException, NoAccessException {
        String id = String.valueOf(getDate().getYear()).substring(2, 4); //Get current year and only last two digits
        id += String.format("%04d", projects.size()); //Add a running 4-digit number equal to number of projects.
        
        Project newProject = new Project(id, startDate, endDate, dateServer);

        projects.add(newProject);

        return newProject;
    }
    
    public void assignManagerToProject(Project project) throws AlreadyAssignedProjectManagerException{
    	if(project.getProjectManager() != null) {
    		throw new AlreadyAssignedProjectManagerException("The project already has a Project Manager.");
    	}
    	
    	project.assignProjectManager(this.currentEmployee);
    }
    
    public boolean changeNameOfProject(Project project, String name) throws NoAccessException, InvalidNameException {
    	if(this.manageProject(project))  {
    		project.setName(name);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean manageProjectDates(Project project, LocalDate startDate, LocalDate endDate) throws NoAccessException, WrongDateException {
    	if(this.manageProject(project)) {
	    	if(startDate != null)
	    		project.setStartDate(startDate);
	    	
	    	if(endDate != null)
	    		project.setEndDate(endDate);
	    	return true;
	    } else {
	    	return false;
	    }
    }
    
    public ProjectActivity createActivityForProject(Project project, String activityType, int startWeek, int endWeek, int approximatedHours) throws NoAccessException, IncorrectAttributeException {
    	if(this.manageProject(project)) {
    		LocalDate startDate = LocalDate.now().with(ChronoField.ALIGNED_WEEK_OF_YEAR, startWeek).with(DayOfWeek.MONDAY);
    		LocalDate endDate = LocalDate.now().with(ChronoField.ALIGNED_WEEK_OF_YEAR, endWeek).with(DayOfWeek.MONDAY);
    		
    		return project.createActivity(activityType, startDate, endDate, approximatedHours);
    	} else {
    		return null;
    	}
    }
    
    public boolean endProject(Project project) throws NoAccessException {
    	if(this.manageProject(project)) {
    		project.end();
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean manageProject(Project project) throws NoAccessException {
    	if(project.getProjectManager() == null || !project.getProjectManager().equals(this.currentEmployee)) {
    		throw new NoAccessException("Current user is not Project Manager for this project.");
    	}
    	
    	return true;
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

    public LocalDate getDate() {
        return dateServer.getDate();
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
