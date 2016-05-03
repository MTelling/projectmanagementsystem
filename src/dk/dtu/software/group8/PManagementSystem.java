package dk.dtu.software.group8;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import dk.dtu.software.group8.Exceptions.*;

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

    public Project createProject(LocalDate startDate, LocalDate endDate) throws WrongDateException {
        if(startDate == null || endDate == null) {
            throw new WrongDateException("Missing date(s)!");
        } else if(startDate.isBefore(dateServer.getDate())) {
            throw new WrongDateException("Start Date is not allowed to be in the past!");
        } else if(endDate.isBefore(startDate)) {
            throw new WrongDateException("End Date is not allowed to be before Start Date!");
        }

        String id = String.valueOf(getDate().getYear()).substring(2, 4); //Get current year and only last two digits
        id += String.format("%04d", projects.size()); //Add a running 4-digit number equal to number of projects.
        
        Project newProject = new Project(id, startDate, endDate);

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
	    	if(startDate != null
                    && (startDate.isAfter(dateServer.getDate()) || startDate.isEqual(dateServer.getDate()))) {
                project.setStartDate(startDate);
            } else {
                throw new WrongDateException("Start Date is not allowed to be in the past!");
            }
	    	
	    	if(endDate != null && (endDate.isEqual(startDate) || endDate.isAfter(startDate))) {
                project.setEndDate(endDate);
            } else {
                throw new WrongDateException("End Date is not allowed to be before Start Date!");
            }
	    	return true;
	    } else {
	    	return false;
	    }
    }
    
    public ProjectActivity createActivityForProject(Project project, String activityType, YearWeek startWeek, YearWeek endWeek, int approximatedHours) throws NoAccessException, IncorrectAttributeException {
    	if(this.manageProject(project)) {

    		return project.createActivity(activityType, startWeek, endWeek, approximatedHours);
    	} else {
    		return null;
    	}
    }
    
    public boolean endProject(Project project) throws NoAccessException {
    	if(this.manageProject(project)) {
            try {
                project.setEndDate(dateServer.getDate());
            } catch (Exception e) { }
    		return true;
    	} else {
    		return false;
    	}
    }

    public boolean manageActivityDates(Project project, Activity activity, YearWeek startWeek, YearWeek endWeek) throws IncorrectAttributeException, NoAccessException, WrongDateException {
        if(this.manageProject(project)) {
            if(!project.getActivities().contains(activity)) {
                throw new IncorrectAttributeException("Invalid Activity: Project does not contain supplied activity!");
            } else {
                if(startWeek != null && startWeek.isAfter(YearWeek.fromDate(dateServer.getDate()))) {
                    activity.setStartWeek(startWeek);
                } else {
                    throw new WrongDateException("Start Week is not allowed to be in the past!");
                }

                if(endWeek != null && (endWeek.isEqual(startWeek) || endWeek.isAfter(startWeek))) {
                    activity.setEndWeek(endWeek);
                } else {
                    throw new WrongDateException("End Week is not allowed to be before Start Week!");
                }

                return true;
            }
        }
        return false;
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

    public boolean addEmployeeToActivity(Project project, ProjectActivity activity, Employee employee) throws NoAccessException, TooManyActivitiesException {
        if(this.manageProject(project)) {
            return project.addEmployeeToActivity(activity, employee);
        }
        return false;
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

    public void registerWorkHours(ProjectActivity activity, int minutes, LocalDate day) throws TooManyHoursException, NegativeHoursException, NoAccessException, WrongDateException {
        if(day.isAfter(this.getDate())) {
            throw new WrongDateException("You can not register work hours in the future.");
        } else {
            this.getCurrentEmployee().registerWorkHours(activity, minutes, day);
        }
    }
}
