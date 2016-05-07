package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Marcus
 */
public class PManagementSystem {

    private DatabaseManager db;
    private DateServer dateServer;
	private Employee currentEmployee;
    private List<Project> projects;

    /**
     * Created by Tobias
     */
    public PManagementSystem() throws IOException {

        Locale.setDefault(Locale.UK);

        dateServer = new DateServer();

        db = new DatabaseManager("Employees.txt");

        projects = new LinkedList<>();
    }

    /**
     * Created by Marcus
     */
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

    /**
     * Created by Morten
     */
    public void assignManagerToProject(Project project) throws AlreadyAssignedProjectManagerException{
    	if(project.getProjectManager() != null) {
    		throw new AlreadyAssignedProjectManagerException("The project already has a Project Manager.");
    	}
    	
    	project.assignProjectManager(this.currentEmployee);
    }

    /**
     * Created by Tobias
     */
    public void addEmployeeToActivity(ProjectActivity activity, Employee employee)
            throws NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed {

        if (employee == null) {
            throw new NullNotAllowed("You need to choose an employee.");
        }

        Project project = activity.getProject();

        if(this.manageProject(project))
            project.addEmployeeToActivity(activity, employee);
    }

    /**
     * Created by Marcus
     */
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

    /**
     * Created by Morten
     */
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

    /**
     * Created by Tobias
     */
    public ProjectActivity createActivityForProject(Project project, String activityType, YearWeek startWeek, YearWeek endWeek, int approximatedHours) throws NoAccessException, IncorrectAttributeException, WrongDateException {
    	ProjectActivity projectActivity = null;

        if(this.manageProject(project)) {
            //Check that the start and end dates do not exceed the projects.
            if (startWeek.isBefore(YearWeek.fromDate(project.getStartDate()))) {
                throw new WrongDateException("The given start week is before the start of the project.");
            }

            if (endWeek.isAfter(YearWeek.fromDate(project.getEndDate()))) {
                throw new WrongDateException("The given end week exceeds the duration of the project.");
            }

    		projectActivity = project.createActivity(activityType, startWeek.toLocalDate(), endWeek.toLocalDate().plusDays(6), approximatedHours, project);
    	}

        return projectActivity;
    }

    /**
     * Created by Marcus
     */
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

    /**
     * Created by Morten
     */
    public void endProject(Project project) throws NoAccessException, WrongDateException {
    	if(this.manageProject(project)) {

            project.setEndDate(dateServer.getDate());

        }
    }

    /**
     * Created by Tobias
     */
    public void manageActivityDates(ProjectActivity activity, YearWeek startWeek, YearWeek endWeek) throws IncorrectAttributeException, NoAccessException, WrongDateException {
        if(this.manageProject(activity.getProject())) {
            if(startWeek != null && startWeek.isAfter(YearWeek.fromDate(dateServer.getDate()))) {
                activity.setStartDate(startWeek.toLocalDate());
            } else {
                throw new WrongDateException("Start Week is not allowed to be in the past!");
            }

            if(endWeek != null && (endWeek.equals(startWeek) || endWeek.isAfter(startWeek))) {
                activity.setEndDate(endWeek.toLocalDate().plusDays(6));
            } else {
                throw new WrongDateException("End Week is not allowed to be before Start Week!");
            }

            //Check that the start and end dates do not exceed the projects.
            if (startWeek.isBefore(YearWeek.fromDate(activity.getProject().getStartDate()))) {
                throw new WrongDateException("The given start week is before the start of the project.");
            }

            if (endWeek.isAfter(YearWeek.fromDate(activity.getProject().getEndDate()))) {
                throw new WrongDateException("The given end week exceeds the duration of the project.");
            }
        }
    }

    /**
     * Created by Marcus
     */
    private boolean manageProject(Project project) throws NoAccessException {
    	if(project.getProjectManager() == null || !project.getProjectManager().equals(this.currentEmployee)) {
    		throw new NoAccessException("Current user is not Project Manager for this project.");
    	}
    	
    	return true;
    }

    /**
     * Created by Morten
     */
    public PersonalActivity createPersonalActivityForEmployee(String activityType, LocalDate startDate, LocalDate endDate, Employee emp) throws WrongDateException, IncorrectAttributeException {
        return emp.createPersonalActivity(activityType, startDate, endDate);
    }

    /**
     * Created by Tobias
     */
    public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

    /**
     * Created by Marcus
     */
    public Employee getEmployeeFromId(String iD) throws InvalidEmployeeException {
        Optional<Employee> emp = db.getEmployees().stream().filter(e -> e.getId().equals(iD)).findFirst();

        if (emp.isPresent()) {
            return emp.get();
        } else {
            throw new InvalidEmployeeException("No employee with that name is in the system.");
        }
    }

    /**
     * Created by Morten
     */
    public boolean signIn(String iD) {
        Optional<Employee> emp = db.getEmployees().stream().filter(e -> e.getId().equals(iD)).findFirst();

        if (emp.isPresent()) {
            this.currentEmployee = emp.get();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Created by Tobias
     */
    public String extractWorkReport(Project project) throws IOException, NoAccessException {
        String workReportPath = null;
        if (this.manageProject(project)) {
            WorkReport workReport = new WorkReport(this, project);
            workReportPath = workReport.make();
        }

        return workReportPath;
    }

    /**
     * Created by Tobias
     */
    public void addEmployeeToActivityAsConsultant(ProjectActivity activity, Employee employee) throws NoAccessException, InvalidEmployeeException, EmployeeAlreadyAddedException {
        if(!activity.getEmployees().contains(this.currentEmployee)) {
            throw new NoAccessException("Current user is not assigned to this activity.");
        } else if (activity.getConsultants().contains(employee)) {
            throw new EmployeeAlreadyAddedException("Employee is already added as consultant.");
        } else {
            employee.assignConsultantToActivity(activity);
        }
    }

    /**
     * Created by Marcus
     */
    public LocalDate getDate() {
        return dateServer.getDate();
    }

    /**
     * Created by Morten
     */
    public List<Employee> getEmployees() {
        return this.db.getEmployees();
    }

    /**
     * Created by Tobias
     */
    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

    /**
     * Created by Marcus
     */
    public boolean userLoggedIn() {
        return currentEmployee != null;
    }

    /**
     * Created by Morten
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Created by Tobias
     */
    public void registerWorkHours(ProjectActivity activity, int minutes, LocalDate day) throws TooManyHoursException, NegativeHoursException, NoAccessException, WrongDateException {
        if(day.isAfter(this.getDate())) {
            throw new WrongDateException("You can not register work hours in the future.");
        } else {
            this.getCurrentEmployee().registerWorkHours(activity, minutes, day);
        }
    }

    /**
     * Created by Marcus
     */
    public List<Activity> getEmployeeActivitiesOnDate(Employee emp, LocalDate date) {
        List<Activity> activitiesOnDate = null;

        if(emp != null && date != null) {
            activitiesOnDate = emp.getActivitiesOnDate(date);
        }
        return activitiesOnDate;
    }

    /**
     * Created by Morten
     */
    public void changeActivityApproximatedHours(ProjectActivity projectActivity, int hours) throws NoAccessException, NegativeHoursException {
        if(this.manageProject(projectActivity.getProject()))
            projectActivity.changeApproximatedHours(hours);
    }

    /**
     * Created by Tobias
     */
    public void removeActivityFromProject(ProjectActivity projectActivity) throws NoAccessException, InvalidActivityException {
        if(this.manageProject(projectActivity.getProject()))
            projectActivity.getProject().removeActivity(projectActivity);
    }

    /**
     * Created by Marcus
     */
    public void removePersonalActivity(PersonalActivity personalActivity) throws InvalidActivityException {
        this.getCurrentEmployee().removePersonalActivity(personalActivity);
    }
}