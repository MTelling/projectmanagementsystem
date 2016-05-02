package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {
	
	private String id;
    private String firstName;
    private String lastName;

	private List<Activity> currentActivities;
	private List<Activity> personalActivities;
	
	public Employee(String id, String firstName, String lastName) {
		this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
		this.currentActivities = new LinkedList<>();
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

	public List<Activity> getPersonalActivities() {
		return this.personalActivities;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
