package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;

import java.util.LinkedList;
import java.util.List;

public class Employee {
	
	private String id;
    private String firstName;
    private String lastName;

	private List<Activity> currentActivities;
	
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

	public void assignToActivity(ProjectActivity projectActivity) throws TooManyActivitiesException {
		if (currentActivities.size() < 20) {
			currentActivities.add(projectActivity);
		} else {
			throw new TooManyActivitiesException("Employee is assigned to too many activities in given period.");
		}
	}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
