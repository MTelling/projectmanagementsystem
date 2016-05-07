package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class PManagementSystem {

    private DatabaseManager db;
    private DateServer dateServer;
	private Employee currentEmployee;
    private List<Project> projects;

    public PManagementSystem() throws IOException {

        Locale.setDefault(Locale.UK);
        
        dateServer = new DateServer();

        db = new DatabaseManager("Employees.txt");

        projects = new LinkedList<>();
    }

    public Project createProject(LocalDate startDate, LocalDate endDate) throws WrongDateException {
        if(startDate == null || endDate == null) {
            throw new WrongDateException("Missing date(s)!");
        } else if(startDate.isBefore(dateServer.getDate())) {
            throw new WrongDateException("Start Date is not allowed to be in the past!");
        } else if(endDate.isBefore(startDate)) {
            throw new WrongDateException("End Date is not allowed to be before Start Date!");
        }

        String id = String.valueOf(getDate().getYear()).substring(2, 4); //Get current year and only last two digits
        id += String.format("%04d", projects.size()); //Add a running 4-digit number equal to number of projects.
        
        Project newProject = new Project(id, startDate, endDate);

        projects.add(newProject);

        return newProject;
    }
    
    public void assignManagerToProject(Project project) throws AlreadyAssignedProjectManagerException{
    	if(project.getProjectManager() != null) {
    		throw new AlreadyAssignedProjectManagerException("The project already has a Project Manager.");
    	}
    	
    	project.assignProjectManager(this.currentEmployee);
    }

    public void addEmployeeToActivity(Project project, ProjectActivity activity, Employee employee) throws NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException {
        if(this.manageProject(project))
            project.addEmployeeToActivity(activity, employee);
    }

    public void changeNameOfProject(Project project, String name) throws NoAccessException, InvalidNameException {
    	if(this.manageProject(project)) {

            Optional<Project> projectsQuery = projects
                    .stream()
                    .filter(p -> {
                        if (p.getName() != null) {
                            return p.getName().equals(name);
                        } else {
                            return false;
                        }
                    }).findAny();

            if (!projectsQuery.isPresent()) {
                project.setName(name);
            } else { //If there is already a project with the same name throw an exception.
                throw new InvalidNameException("Name is already assigned to another project.");
            }
        }
    }
    
    public void manageProjectDates(Project project, LocalDate startDate, LocalDate endDate) throws NoAccessException, WrongDateException {
    	if(this.manageProject(project)) {
            if (startDate != null
                    && (startDate.isAfter(dateServer.getDate()) || startDate.isEqual(dateServer.getDate()))) {
                project.setStartDate(startDate);
            } else {
                throw new WrongDateException("Start Date is not allowed to be in the past!");
            }

            if (endDate != null && (endDate.isEqual(startDate) || endDate.isAfter(startDate))) {
                project.setEndDate(endDate);
            } else {
                throw new WrongDateException("End Date is not allowed to be before Start Date!");
            }
        }
    }
    
    public ProjectActivity createActivityForProject(Project project, String activityType, YearWeek startWeek, YearWeek endWeek, int approximatedHours) throws NoAccessException, IncorrectAttributeException {
    	ProjectActivity projectActivity = null;

        if(this.manageProject(project)) {
    		projectActivity = project.createActivity(activityType, startWeek.toLocalDate(), endWeek.toLocalDate().plusDays(6), approximatedHours, project);
    	}

        return projectActivity;
    }

    public List<Employee> findAvailableEmployees(LocalDate startDate, LocalDate endDate, ProjectActivity activity) throws WrongDateException {
        if(startDate.isBefore(dateServer.getDate()) || endDate.isBefore(startDate)) {
            throw new WrongDateException("Time period is invalid!");
        }

        List<Employee> availableEmployees = new ArrayList<>();
            for(Employee emp : this.getEmployees()) {
                if(emp.isAvailable(startDate, endDate, activity)) availableEmployees.add(emp);
            }
        return availableEmployees;
    }

    public boolean endProject(Project project) throws NoAccessException {
    	if(this.manageProject(project)) {
            try {
                project.setEndDate(dateServer.getDate());
            } catch (Exception e) { }
    		return true;
    	} else {
    		return false;
    	}
    }

    //TODO: Can we put this inside the project class?
    //TODO: Now an activity has it's project, this could be done simpler. Just remove the project parameter.
    public boolean manageActivityDates(Project project, Activity activity, YearWeek startWeek, YearWeek endWeek) throws IncorrectAttributeException, NoAccessException, WrongDateException {
        if(this.manageProject(project)) {
            if(!project.getActivities().contains(activity)) {
                throw new IncorrectAttributeException("Invalid Activity: Project does not contain supplied activity!");
            } else {




                if(startWeek != null
                        && startWeek.isAfter(YearWeek.fromDate(dateServer.getDate()))) {
                    activity.setStartDate(startWeek.toLocalDate());
                } else {
                    throw new WrongDateException("Start Week is not allowed to be in the past!");
                }

                if(endWeek != null && (endWeek.equals(startWeek) || endWeek.isAfter(startWeek))) {
                    activity.setEndDate(endWeek.toLocalDate().plusDays(6));
                } else {
                    throw new WrongDateException("End Week is not allowed to be before Start Week!");
                }

                //TODO: This could be added, but makes a lot of other tests fail.
                //Tager vi til sidst sammen.
//                if (startWeek != null && startWeek.isBefore(YearWeek.fromDate(project.getStartDate()))) {
//                    throw new WrongDateException("The given end week exceeds the duration of the project.");
//                }
//
//                if (endWeek != null && endWeek.isAfter(YearWeek.fromDate(project.getEndDate()))) {
//                    throw new WrongDateException("The given end week exceeds the duration of the project.");
//                }

                return true;
            }
        }
        return false;
    }
    
    private boolean manageProject(Project project) throws NoAccessException {
    	if(project.getProjectManager() == null || !project.getProjectManager().equals(this.currentEmployee)) {
    		throw new NoAccessException("Current user is not Project Manager for this project.");
    	}
    	
    	return true;
    }

    public PersonalActivity createPersonalActivityForEmployee(String activityType, LocalDate startDate, LocalDate endDate, Employee emp) throws WrongDateException, IncorrectAttributeException {
        return emp.createPersonalActivity(activityType, startDate, endDate);
    }

    public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

    public Employee getEmployeeFromId(String iD) throws InvalidEmployeeException {
        Optional<Employee> emp = db.getEmployees().stream().filter(e -> e.getId().equals(iD)).findFirst();

        if (emp.isPresent()) {
            return emp.get();
        } else {
            throw new InvalidEmployeeException("No employee with that name is in the system.");
        }
    }

    public boolean signIn(String iD) {
        //TODO: This could be done smarter with the help of getEmployeeFromId. We should look on that. It should probably just throw the same exception.

        Optional<Employee> emp = db.getEmployees().stream().filter(e -> e.getId().equals(iD)).findFirst();

        if (emp.isPresent()) {
            this.currentEmployee = emp.get();
            return true;
        } else {
            return false;
        }

    }

    public boolean addEmployeeToActivityAsConsultant(ProjectActivity activity, Employee employee)
            throws NoAccessException, InvalidEmployeeException, EmployeeAlreadyAddedException {
        if(!this.userLoggedIn()) {
            throw new NoAccessException("User is not logged in.");
        } else if(!activity.getEmployees().contains(this.currentEmployee)) {
            throw new NoAccessException("Current user is not assigned to this activity.");
        } else if (activity.getConsultants().contains(employee)) {
            throw new EmployeeAlreadyAddedException("Employee is already added as consultant.");
        } else if (!this.db.getEmployees().contains(employee)) {
            //TODO: Would we ever get here? Maybe pass the employee as a name instead and let pms find the emp?
            throw new InvalidEmployeeException("No employee with that name is in the system.");
        } else {
            return employee.assignConsultantToActivity(activity);
        }
    }

    public LocalDate getDate() {
        return dateServer.getDate();
    }

    public List<Employee> getEmployees() {
        return this.db.getEmployees();
    }

    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

    public boolean userLoggedIn() {
        return currentEmployee != null;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void registerWorkHours(ProjectActivity activity, int minutes, LocalDate day) throws TooManyHoursException, NegativeHoursException, NoAccessException, WrongDateException {
        if(day.isAfter(this.getDate())) {
            throw new WrongDateException("You can not register work hours in the future.");
        } else {
            this.getCurrentEmployee().registerWorkHours(activity, minutes, day);
        }
    }

    public List<Activity> getEmployeeActivitiesOnDate(Employee emp, LocalDate date) {
        if(emp != null && date != null) {
            return emp.getActivitiesOnDate(date);
        }
        return null;
    }

    public void changeActivityApproximatedHours(Project project, ProjectActivity activity, int hours) throws NoAccessException, NegativeHoursException {
        if(this.manageProject(project))
            activity.changeApproximatedHours(hours);
    }

    public void removeActivityFromProject(Project project, ProjectActivity projectActivity) throws NoAccessException, InvalidActivityException {
        if(this.manageProject(project))
            project.removeActivity(projectActivity);
    }

    public void removePersonalActivity(PersonalActivity personalActivity) throws InvalidActivityException {
        this.getCurrentEmployee().removePersonalActivity(personalActivity);
    }
}
