package dk.dtu.software.group8;

import java.util.Calendar;

public class Project {

    private String iD;
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private Employee projectManager;

    public Project(Calendar startDate, Calendar endDate, String iD) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.iD = iD;
    }

    public String extractReport() {
        return null;
    }

    public boolean assignProjectManager(Employee employee) {
        return false;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public String getId() {
        return iD;
    }
}
