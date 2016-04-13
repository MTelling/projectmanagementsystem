package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.runner.Description;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Project {

    private String iD;
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private Employee projectManager;
    private DateServer dateServer;
    private List<Activity> activities;

    public Project(DateServer dateServer,
                   Calendar startDate,
                   Calendar endDate,
                   String iD) throws WrongDateException, NoAccessException {

        if(startDate == null || endDate == null) {
            throw new WrongDateException("Missing date(s).");
        }

        this.activities = new ArrayList<>();

        this.dateServer = dateServer;

        setEndDate(endDate, null);
        setStartDate(startDate, null);

        this.iD = iD;
    }

    //TODO: In the class diagram, this method returns a boolean. Should it?
    public void end() {
        this.endDate = Calendar.getInstance();
    }

    public String extractReport() {
        return null;
    }

    public void assignProjectManager(Employee employee) {
        this.projectManager = employee;
    }

    public Calendar getEndDate() {
        return endDate;
    }


    public void setEndDate(Calendar endDate, Employee employee) throws WrongDateException, NoAccessException {
        checkIfEmployeeIsProjectManager(employee);

        if (endDate.before(startDate)) {
            throw new WrongDateException("End date is before start date.");
        }
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate, Employee employee) throws WrongDateException, NoAccessException {
        checkIfEmployeeIsProjectManager(employee);

        if (startDate.after(endDate)) {
            throw new WrongDateException("End date is before start date.");
        } else if (startDate.before(dateServer.getCalendar())) {
            throw new WrongDateException("Date is in the past.");
        }
        this.startDate = startDate;
    }

    public String getId() {
        return iD;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    private void checkIfEmployeeIsProjectManager(Employee emp) throws NoAccessException {
        if (this.projectManager != null && !this.projectManager.equals(emp)) {
            throw new NoAccessException("Current user is not Project Manager for this project.");
        }
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

    public String getName() { return this.name; }

    public List<Activity> getActivities() {
        return activities;
    }

    public Activity createActivity(String type, int startWeek, int endWeek, int approximatedHours) throws IncorrectAttributeException {
        Activity newActivity = new ProjectActivity(type, startWeek, endWeek, approximatedHours);
        this.activities.add(newActivity);
        return newActivity;
    }
}
