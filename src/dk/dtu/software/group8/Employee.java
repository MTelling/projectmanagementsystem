package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import dk.dtu.software.group8.Exceptions.*;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Employee {
	
	private String id;
    private String firstName;
    private String lastName;

    private List<Activity> currentActivities;
    private List<Activity> currentConsultants;
	private List<RegisteredWork> registeredWork = new ArrayList<RegisteredWork>();
	private List<Activity> personalActivities;

	public Employee(String id, String firstName, String lastName) {
		this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
		this.currentActivities = new LinkedList<>();
        this.currentConsultants = new LinkedList<>();
		this.personalActivities = new LinkedList<>();
	}

	public boolean assignToActivity(ProjectActivity projectActivity) throws TooManyActivitiesException {
		if (currentActivities.size() < 20) {
			currentActivities.add(projectActivity);
			return true;
		} else {
			throw new TooManyActivitiesException("Employee is assigned to too many activities in given period.");
		}
	}

    public PersonalActivity createPersonalActivity(String activityType, LocalDate startDate, LocalDate endDate) throws WrongDateException, IncorrectAttributeException {
        boolean isOccupied = false;
        for (Activity a: personalActivities) {
            if(a.isTimePeriodInActivityDuration(startDate, endDate))
                isOccupied = true;
        }

        if(isOccupied) {
            throw new WrongDateException("Time period is already occupied by another personal activity!");
        }

        PersonalActivity pa = new PersonalActivity(activityType, startDate, endDate);
        this.personalActivities.add(pa);
        return pa;
    }

	public boolean isAvailable(LocalDate startDate, LocalDate endDate, ProjectActivity activity) {
		if(this.currentActivities.contains(activity))
            return false;

        Optional<Activity> personalQuery = this.personalActivities.stream()
                .filter(pa -> pa.isTimePeriodInActivityDuration(startDate, endDate))
                .findAny();

        if(personalQuery.isPresent()) {
            return false;
        } else {
            List<Activity> projectQuery = this.currentActivities.stream()
                    .filter(pa -> pa.isTimePeriodInActivityDuration(startDate, endDate))
                    .collect(Collectors.toList());
            return projectQuery.size() < 20;
        }
	}

	public List<Activity> getPersonalActivities() {
		return this.personalActivities;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

    public boolean assignConsultantToActivity(ProjectActivity projectActivity) throws InvalidEmployeeException {
        if (projectActivity.assignConsultantToActivity(this)) {
            currentConsultants.add(projectActivity);
            return true;
        } else {
            return false;
        }
    }

	public void registerWorkHours(ProjectActivity activity, int minutes, LocalDate day) throws NoAccessException, TooManyHoursException, NegativeHoursException {
		if(!currentActivities.contains(activity)) { // Test if employee is assigned to activity
			throw new NoAccessException("Current user not assigned to activity.");
		} else {
			// Check if employee has already registered work for this activity and day
			List<RegisteredWork> empWorkDayQuery = this.registeredWork
					.stream()
					.filter(
							e -> (e.getDay().equals(day))
					)
					.collect(Collectors.toList());
			Optional<RegisteredWork> empWorkQuery = empWorkDayQuery
					.stream()
					.filter(
							e -> (e.matches(this, activity, day))
					)
					.findAny();

			int workRegisteredThisDay = 0;
			for(RegisteredWork work : empWorkDayQuery) {
				workRegisteredThisDay += work.getMinutes();
			}

			if(empWorkQuery.isPresent()) {
				int deltaMinutes = minutes - empWorkQuery.get().getMinutes();

				if(workRegisteredThisDay + deltaMinutes > 1440) {
					throw new TooManyHoursException("You can not work more than 24 hours in one day.");
				}

				empWorkQuery.get().addWork(deltaMinutes);
			} else {

				if(workRegisteredThisDay + minutes > 1440) {
					throw new TooManyHoursException("You can not work more than 24 hours in one day.");
				}

				RegisteredWork jobDone = new RegisteredWork(this, activity, day, minutes);
				this.registeredWork.add(jobDone);
				activity.registerWork(jobDone);
			}
		}
	}

	public boolean matches(Employee employee) {
		return
				this.getId().equals(employee.getId())
				&& this.getFirstName().equals(employee.getFirstName())
				&& this.getLastName().equals(employee.getLastName());
	}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

	public List<RegisteredWork> getRegisteredWork() { return this.registeredWork; }
}
