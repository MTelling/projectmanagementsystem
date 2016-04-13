package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import jdk.internal.dynalink.linker.LinkerServices;

import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectActivity extends  Activity {

    private int approximatedHours;

    //TODO: This should not be hardcoded here.
    private static final Set<String> LEGAL_TYPES = new HashSet<String>(Arrays.asList("Implementation"));

    private List<Employee> assignedEmployees;

    //TODO: What if endWeek is week 2, year 2017 and startWeek is week 47, year 2016. How do we check this?
    public ProjectActivity(String activityType,int startWeek,int endWeek,int approximatedHours) throws IncorrectAttributeException {
        //TODO: Should we check for activity types? Can't they be everything?
        if(!LEGAL_TYPES.contains(activityType)) {
            throw new IncorrectAttributeException("The supplied activity type is not a correct activity type.");
        } else if (endWeek - startWeek < 0 ||
                Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date())) > startWeek) {
            String message = "The supplied time period is not a legal time period (Start before now or end before start).";
            throw new IncorrectAttributeException(message);
        } else if (approximatedHours < 1) {
            throw new IncorrectAttributeException("The supplied approximated time is not allowed to be negative.");
        }

        this.activityType = activityType;

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.WEEK_OF_YEAR, startWeek);
        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.WEEK_OF_YEAR, endWeek);

        this.startTime = startTime;
        this.endTime = endTime;
        this.approximatedHours = approximatedHours;

        assignedEmployees = new ArrayList<Employee>();
    }

    public int getApproximatedHours() {
        return this.approximatedHours;
    }

    public void addEmployee(Employee employee) {
        //TODO: if(employee is employed) {
        assignedEmployees.add(employee);
        //} else {
            //throw new RedAlertException("You are not employed here, you freeloader!");
        //}
    }

    public List<Employee> getEmployees() {
        return this.assignedEmployees;
    }
}
