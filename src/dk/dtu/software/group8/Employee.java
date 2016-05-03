package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.NegativeHoursException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;
import dk.dtu.software.group8.Exceptions.TooManyHoursException;

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
	private List<RegisteredWork> registeredWork = new ArrayList<RegisteredWork>();
	
	public Employee(String id, String firstName, String lastName) {
		this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
		this.currentActivities = new LinkedList<>();
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public boolean assignToActivity(ProjectActivity projectActivity) throws TooManyActivitiesException {
		if (currentActivities.size() < 20) {
			currentActivities.add(projectActivity);
            return true;
		} else {
			throw new TooManyActivitiesException("Employee is assigned to too many activities in given period.");
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
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

	public List<RegisteredWork> getRegisteredWork() { return this.registeredWork; }

	public List<Activity> getCurrentActivities() {
        return currentActivities;
    }

	public String toString() {
		return firstName + " " + lastName;
	}
}
