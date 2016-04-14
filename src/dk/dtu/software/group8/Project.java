package dk.dtu.software.group8;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

public class Project {

    private String id, name;
    private LocalDate startDate, endDate;
    private Employee projectManager;
    private DateServer dateServer;
    private List<Activity> activities;

    public Project(String id, LocalDate startDate, LocalDate endDate, DateServer dateServer) throws WrongDateException, NoAccessException {
        if(startDate == null || endDate == null) {
            throw new WrongDateException("Missing date(s).");
        }

        this.id = id;
        
        this.activities = new ArrayList<Activity>();
        this.dateServer = dateServer;
        
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

    public void assignProjectManager(Employee employee) throws AlreadyAssignedProjectManagerException {
        if (this.projectManager == null) {
            this.projectManager = employee;
        } else {
            throw new AlreadyAssignedProjectManagerException("The project already has a Project Manager.");
        }
    }
    
    public void setStartDate(LocalDate startDate) throws WrongDateException {
        if (startDate.isBefore(dateServer.getDate())) {
            throw new WrongDateException("Date is in the past.");
        }
        this.startDate = startDate;
    }
    
    public void setEndDate(LocalDate endDate) throws WrongDateException {
        if (endDate.isBefore(startDate)) {
            throw new WrongDateException("End date is before start date.");
        }
        this.endDate = endDate;
    }
    
    public ProjectActivity createActivity(String type, int startWeek, int endWeek, int approximatedHours) throws IncorrectAttributeException {

        ProjectActivity newActivity = new ProjectActivity(type, startWeek, endWeek, approximatedHours);
        this.activities.add(newActivity);
        return newActivity;
    }

    public void addEmployeeToActivity(ProjectActivity activity, Employee employee) {
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
    
    public void end() {
        this.endDate = dateServer.getDate();
    }

}
