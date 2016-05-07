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
    private List<ProjectActivity> activities;

    /**
     * Created by Morten
     */
    public Project(String id, LocalDate startDate, LocalDate endDate) throws WrongDateException {
        this.id = id;
        
        this.activities = new ArrayList<>();
        
        setStartDate(startDate);
        setEndDate(endDate);
    }

    /**
     * Created by Tobias
     */
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

    /**
     * Created by Marcus
     */
    public void assignProjectManager(Employee employee) {
    	this.projectManager = employee;
    }

    /**
     * Created by Morten
     */
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    /**
     * Created by Tobias
     */
    public void setEndDate(LocalDate endDate) throws WrongDateException { this.endDate = endDate; }

    /**
     * Created by Marcus
     */
    public ProjectActivity createActivity(String type, LocalDate startDate, LocalDate endDate, int approximatedHours, Project project) throws IncorrectAttributeException {
        ProjectActivity newActivity = new ProjectActivity(type, startDate, endDate, approximatedHours, project);
        this.activities.add(newActivity);
        return newActivity;
    }

    /**
     * Created by Morten
     */
    public void addEmployeeToActivity(ProjectActivity activity, Employee employee) throws TooManyActivitiesException, EmployeeAlreadyAddedException {
         activity.addEmployee(employee);
    }

    /**
     * Created by Tobias
     */
    public String getId() {
        return id;
    }

    /**
     * Created by Marcus
     */
    public Employee getProjectManager() {
        return projectManager;
    }

    /**
     * Created by Morten
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Created by Tobias
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Created by Marcus
     */
    public String getName() { return this.name; }

    /**
     * Created by Morten
     */
    public List<ProjectActivity> getActivities() {
        return activities;
    }

    /**
     * Created by Tobias
     */
    public String extractReport() {
        return null;
    }

    /**
     * Created by Marcus
     */
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

    /**
     * Created by Morten
     */
    public void removeActivity(ProjectActivity projectActivity) throws InvalidActivityException {
        if(!this.getActivities().contains(projectActivity))
            throw new InvalidActivityException("The activity does not belong to the project!");
        else
            this.getActivities().remove(projectActivity);
    }
}