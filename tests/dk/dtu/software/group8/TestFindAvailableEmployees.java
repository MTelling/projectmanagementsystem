package dk.dtu.software.group8;


import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class TestFindAvailableEmployees extends TestManageProject {

    ProjectActivity activity;

    /**
     * Created by Morten
     */
    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException, WrongDateException {
        activity = pms.createActivityForProject(project, "Unit Testing", new YearWeek(2016, 37), new YearWeek(2016, 42), 42);
        assertThat(activity, instanceOf(ProjectActivity.class));
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testFindAvailableEmployees() throws Exception {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-15");

        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);
        assertThat(availableEmployees, equalTo(pms.getEmployees()));
    }

    /**
     * Created by Marcus
     */
    @Test
    public void testEmployeeWith1Activity() throws Exception {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-15");

        Employee randomEmployee = pms.getEmployees().get(2);

        ProjectActivity activityQ = pms.createActivityForProject(project, "Jon Jones", new YearWeek(2016, 19), new YearWeek(2016, 52), 1850);
        pms.addEmployeeToActivity(activityQ, randomEmployee);

        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);

        assertThat(availableEmployees, equalTo(pms.getEmployees()));
    }

    /**
     * Created by Morten
     */
    @Test
    public void testEmployeeWith20Activities() throws Exception {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-15");

        Employee occupiedEmployee = pms.getEmployees().get(2);

        for(int i = 0; i < 20; i++) {
            ProjectActivity activityQ = pms.createActivityForProject(project, "Jon Jones", new YearWeek(2016, 19), new YearWeek(2016, 52), 1850);
            pms.addEmployeeToActivity(activityQ, occupiedEmployee);
        }

        ArrayList<Employee> actualAvailableEmployees = new ArrayList<>(pms.getEmployees());
        actualAvailableEmployees.remove(occupiedEmployee);

        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);

        assertThat(availableEmployees, equalTo(actualAvailableEmployees));
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testEmployeeWithPersonalActivity() throws Exception {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-15");

        Employee occupiedEmployee = pms.getEmployees().get(2);

        pms.createPersonalActivityForEmployee("Vacation", LocalDate.parse("2016-05-09"), LocalDate.parse("2016-12-31"), occupiedEmployee);

        ArrayList<Employee> actualAvailableEmployees = new ArrayList<>(pms.getEmployees());
        actualAvailableEmployees.remove(occupiedEmployee);

        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);

        assertThat(availableEmployees, equalTo(actualAvailableEmployees));
    }

    /**
     * Created by Marcus
     */
    @Test
    public void testEmployeeAlreadyInActivity() throws Exception {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-15");

        Employee occupiedEmployee = pms.getEmployees().get(2);

        pms.addEmployeeToActivity(activity, occupiedEmployee);

        ArrayList<Employee> actualAvailableEmployees = new ArrayList<>(pms.getEmployees());
        actualAvailableEmployees.remove(occupiedEmployee);

        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);

        assertThat(availableEmployees, equalTo(actualAvailableEmployees));
    }

    /**
     * Created by Morten
     */
    @Test
    public void testStartDateInThePast() throws Exception {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Time period is invalid!");

        LocalDate startDate = LocalDate.parse("2016-04-10");
        LocalDate endDate = LocalDate.parse("2016-05-15");

        pms.findAvailableEmployees(startDate, endDate, activity);
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testEndDateIsBeforeStartDate() throws Exception {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Time period is invalid!");

        LocalDate startDate = LocalDate.parse("2016-05-15");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        pms.findAvailableEmployees(startDate, endDate, activity);
    }
}