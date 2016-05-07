package dk.dtu.software.group8;


import dk.dtu.software.group8.Exceptions.*;

import javax.naming.InvalidNameException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Project {

    private String id, name;
    private LocalDate startDate, endDate;
    private Employee projectManager;
    private List<Activity> activities;

    public Project(String id, LocalDate startDate, LocalDate endDate) throws WrongDateException {
        this.id = id;
        
        this.activities = new ArrayList<Activity>();
        
        setStartDate(startDate);
        setEndDate(endDate);
    }
    
    public void setName(String name) throws InvalidNameException {
        if(name == null) {
            throw new InvalidNameException("Name cannot be null.");
        } else if (name != name.replaceAll("[^\\w\\s\\-_æøåÆØÅ]", "")) {
            throw new InvalidNameException("Special characters are not allowed in name.");
        } else if (name.length() > 30) {
            throw new InvalidNameException("Name can only be 30 characters long.");
        } else {
        	this.name = name;
        }
    }

    public void assignProjectManager(Employee employee) {
    	this.projectManager = employee;
    }
    
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public void setEndDate(LocalDate endDate) throws WrongDateException { this.endDate = endDate; }
    
    public ProjectActivity createActivity(String type, LocalDate startDate, LocalDate endDate, int approximatedHours, Project project) throws IncorrectAttributeException {
        ProjectActivity newActivity = new ProjectActivity(type, startDate, endDate, approximatedHours, project);
        this.activities.add(newActivity);
        return newActivity;
    }

    public void addEmployeeToActivity(ProjectActivity activity, Employee employee) throws TooManyActivitiesException, EmployeeAlreadyAddedException {
         activity.addEmployee(employee);
    }

    public String getId() {
        return id;
    }
    
    public Employee getProjectManager() {
        return projectManager;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }

    public String getName() { return this.name; }

    public List<Activity> getActivities() {
        return activities;
    }

    public String extractReport() {
        return null;
    }

    public String toString() {
        String name = this.getName();

        if (name == null) {
            name = "N/A";
        }

        return id + "\t - \t" + name
                + " (" + this.startDate.toString()
                + " - " + this.endDate.toString()
                + ")";
    }

    public void removeActivity(ProjectActivity projectActivity) throws InvalidActivityException {
        if(!this.getActivities().contains(projectActivity))
            throw new InvalidActivityException("The activity does not belong to the project!");
        else
            this.getActivities().remove(projectActivity);
    }
}
