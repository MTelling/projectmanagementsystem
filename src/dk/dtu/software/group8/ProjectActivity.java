package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Tobias
 */
public class ProjectActivity extends  Activity {

    private int approximatedHours;

    private List<Employee> assignedEmployees;
    private List<Employee> assignedConsultants;
    private List<RegisteredWork> registeredWork = new ArrayList<>();

    private Project project;

    /**
     * Created by Tobias
     */
    public ProjectActivity(String activityType, LocalDate startDate, LocalDate endDate, int approximatedHours, Project project) throws IncorrectAttributeException {

   	    this.project = project;

        if(!activityType.matches("[a-zA-Z ]{3,}")) {
            throw new IncorrectAttributeException("The supplied activity type is not a correct activity type.");
        } else if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
            String message = "The supplied time period is not a legal time period (Start before now or end before start).";
            throw new IncorrectAttributeException(message);
        } else if (approximatedHours < 1) {
            throw new IncorrectAttributeException("The supplied approximated time is not allowed to be negative.");
        }

        this.activityType = activityType;

        this.startTime = startDate;
        this.endTime = endDate;

        this.approximatedHours = approximatedHours;

        this.assignedEmployees = new ArrayList<>();
        this.assignedConsultants = new ArrayList<>();
    }

    /**
     * Created by Marcus
     */
    public List<RegisteredWork> getRegisteredWorkOnDate(LocalDate day) {
        List<RegisteredWork> empWorkDayQuery = this.registeredWork
                .stream()
                .filter(
                        e -> (e.getDay().equals(day))
                )
                .collect(Collectors.toList());

        return empWorkDayQuery;
    }

    /**
     * Created by Morten
     */
    public int getTotalRegisteredMinutesOnDay(LocalDate day) {
        List<RegisteredWork> empWorkDayQuery = getRegisteredWorkOnDate(day);
        int workRegisteredThisDay = 0;
        for(RegisteredWork work : empWorkDayQuery) {
            workRegisteredThisDay += work.getMinutes();
        }

        return workRegisteredThisDay;
    }

    /**
     * Created by Tobias
     */
    public int getTotalRegisteredMinutesPastWeek(LocalDate day) {
        int result = 0;
        for(int i = 0; i < 7; i++) {
            result += getTotalRegisteredMinutesOnDay(day.minusDays(i));
        }
        return result;
    }

    /**
     * Created by Marcus
     */
    public int getTotalRegisteredMinutes() {
        int result = 0;
        for(RegisteredWork work : this.registeredWork) {
            result += work.getMinutes();
        }
        return result;
    }

    /**
     * Created by Morten
     */
    public int getApproximatedHours() {
        return this.approximatedHours;
    }

    /**
     * Created by Tobias
     */
    public void addEmployee(Employee employee) throws TooManyActivitiesException, EmployeeAlreadyAddedException {
        if (assignedEmployees.contains(employee)) {
            throw new EmployeeAlreadyAddedException("That employee has already been assigned to the activity.");
        }

        if (assignedConsultants.contains(employee)) {
            assignedConsultants.remove(employee);
        }

        if(employee.assignToActivity(this)) {
            assignedEmployees.add(employee);
        }
    }

    /**
     * Created by Marcus
     */
    public boolean assignConsultantToActivity(Employee employee) throws InvalidEmployeeException {
        if(this.getEmployees().contains(employee)) {
            throw new InvalidEmployeeException("Employee already assigned to activity.");
        } else {
            assignedConsultants.add(employee);
            return true;
        }
    }

    /**
     * Created by Morten
     */
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

    /**
     * Created by Tobias
     */
    public List<Employee> getEmployees() {
        return this.assignedEmployees;
    }

    /**
     * Created by Marcus
     */
    public List<Employee> getConsultants() { return this.assignedConsultants; }

    /**
     * Created by Morten
     */
    public List<RegisteredWork> getRegisteredWork() { return this.registeredWork; }

    /**
     * Created by Tobias
     */
    public Project getProject() {
        return project;
    }

    /**
     * Created by Marcus
     */
    public int getStartWeek() {
        return YearWeek.fromDate(this.getStartDate()).getWeek();
    }

    /**
     * Created by Morten
     */
    public int getStartYear() {
        return YearWeek.fromDate(this.getStartDate()).getYear();
    }

    /**
     * Created by Tobias
     */
    public int getEndWeek() {
        return YearWeek.fromDate(this.getEndDate()).getWeek();
    }

    /**
     * Created by Marcus
     */
    public int getEndYear() {
        return YearWeek.fromDate(this.getEndDate()).getYear();
    }

    /**
     * Created by Morten
     */
    public void changeApproximatedHours(int hours) throws NegativeHoursException {
        if(hours < 0) throw new NegativeHoursException("Approximated hours can not be negative!");
        else this.approximatedHours = hours;
    }

    /**
     * Created by Tobias
     */
    @Override
    public String toString() {
        return this.activityType
                + " (week " + getStartWeek() + "." + getStartYear()
                + " - week " + getEndWeek() + "." + getEndYear() + ")"
                + " from project: " + this.project.getName();
    }
}