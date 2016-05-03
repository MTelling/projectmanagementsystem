package dk.dtu.software.group8;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;

public class ProjectActivity extends  Activity {

    private int approximatedHours;

    private List<Employee> assignedEmployees;


    public ProjectActivity(String activityType, YearWeek startWeek, YearWeek endWeek, int approximatedHours) throws IncorrectAttributeException {
        if(!activityType.matches("[a-zA-Z ]{3,}")) {
            throw new IncorrectAttributeException("The supplied activity type is not a correct activity type.");
        } else if (startWeek.isBefore(YearWeek.fromDate(LocalDate.now())) || endWeek.isBefore(startWeek)) {
            String message = "The supplied time period is not a legal time period (Start before now or end before start).";
            throw new IncorrectAttributeException(message);
        } else if (approximatedHours < 1) {
            throw new IncorrectAttributeException("The supplied approximated time is not allowed to be negative.");
        }

        this.activityType = activityType;

        this.startTime = startWeek;
        this.endTime = endWeek;

        this.approximatedHours = approximatedHours;

        assignedEmployees = new ArrayList<Employee>();
    }

    public int getApproximatedHours() {
        return this.approximatedHours;
    }

    public void addEmployee(Employee employee) throws TooManyActivitiesException {
        assignedEmployees.add(employee);
        employee.assignToActivity(this);
    }

    public List<Employee> getEmployees() {
        return this.assignedEmployees;
    }
}
