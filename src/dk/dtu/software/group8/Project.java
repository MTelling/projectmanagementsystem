package dk.dtu.software.group8;

import java.util.Calendar;

public class Project {

    private String iD;
    private String name;
    private Calendar startTime;
    private Calendar endTime;
    private Employee projectManager;

    public Project(Calendar startTime, Calendar endTime, String iD) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.iD = iD;
    }

    public String extractReport() {

    }

    public boolean assignProjectManager(Employee employee) {

    }

}
