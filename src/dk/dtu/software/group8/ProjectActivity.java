package dk.dtu.software.group8;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dk.dtu.software.group8.Exceptions.*;

public class ProjectActivity extends  Activity {

    private int approximatedHours;

    private List<Employee> assignedEmployees;
    private List<RegisteredWork> registeredWork = new ArrayList<>();

    private Project project;


    public ProjectActivity(String activityType, YearWeek startWeek, YearWeek endWeek, int approximatedHours, Project project) throws IncorrectAttributeException {

        this.project = project;

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

    public boolean addEmployee(Employee employee) throws TooManyActivitiesException {
        if(employee.assignToActivity(this)) {
            assignedEmployees.add(employee);
            return true;
        }
        return false;
    }

    public void registerWork(RegisteredWork registeredWork) throws TooManyHoursException, NegativeHoursException {
        // Check if employee has already registered work for this activity and day
        Optional<RegisteredWork> empWorkQuery = this.registeredWork
                .stream()
                .filter(
                    e -> (e.matches(registeredWork.getEmployee(), this, registeredWork.getDay()))
                )
                .findAny();

        if(!empWorkQuery.isPresent()) { // Work has not been registered for this day before. Add to list
            this.registeredWork.add(registeredWork);
        }
    }

    public List<Employee> getEmployees() {
        return this.assignedEmployees;
    }

    public List<RegisteredWork> getRegisteredWork() { return this.registeredWork; }

    public Project getProject() {
        return project;
    }
}
