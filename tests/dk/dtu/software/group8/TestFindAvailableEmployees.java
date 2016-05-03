package dk.dtu.software.group8;


import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class TestFindAvailableEmployees extends TestManageProject {

    ProjectActivity activity;

    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException {
        activity = pms.createActivityForProject(project, "Unit Testing", new YearWeek(2017, 37), new YearWeek(2017, 42), 42);
        assertThat(activity, instanceOf(ProjectActivity.class));
    }

//    @Test
//    public void testFindAvailableEmployees() throws Exception {
//        LocalDate startDate = LocalDate.parse("2016-05-10");
//        LocalDate endDate = LocalDate.parse("2016-05-15");
//
//        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);
//        assertThat(availableEmployees, equalTo(db.getEmployees()));
//    }
//
//    @Test
//    public void testEmployeeWith20Activities() throws Exception {
//        LocalDate startDate = LocalDate.parse("2016-05-10");
//        LocalDate endDate = LocalDate.parse("2016-05-15");
//
//        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);
//
//        Employee occupiedEmployee = db.getEmployees().get(2);
//
//        for(int i = 0; i < 20; i++) {
//            ProjectActivity activityQ = pms.createActivityForProject(project, "Jon Jones", new YearWeek(2016, 1), new YearWeek(2016, 52), 1850);
//            pms.addEmployeeToActivity(project, activityQ, occupiedEmployee);
//        }
//
//        List<Employee> actualAvailableEmployees = db.getEmployees();
//        actualAvailableEmployees.remove(occupiedEmployee);
//
//        assertThat(availableEmployees, equalTo(db.getEmployees()));
//    }
//
//    @Test
//    public void testEmployeeWithPersonalActivity() throws Exception {
//        LocalDate startDate = LocalDate.parse("2016-05-10");
//        LocalDate endDate = LocalDate.parse("2016-05-15");
//
//        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);
//
//        Employee occupiedEmployee = db.getEmployees().get(2);
//
//        pms.createPersonalActivityForEmployee("Vacation", LocalDate.parse("2016-01-01"), LocalDate.parse("2016-12-31"), occupiedEmployee);
//
//        List<Employee> actualAvailableEmployees = db.getEmployees();
//        actualAvailableEmployees.remove(occupiedEmployee);
//
//        assertThat(availableEmployees, equalTo(db.getEmployees()));
//    }
//
//    @Test
//    public void testEmployeeAlreadyInActivity() throws Exception {
//        LocalDate startDate = LocalDate.parse("2016-05-10");
//        LocalDate endDate = LocalDate.parse("2016-05-15");
//
//        List<Employee> availableEmployees = pms.findAvailableEmployees(startDate, endDate, activity);
//
//        Employee occupiedEmployee = db.getEmployees().get(2);
//
//        pms.addEmployeeToActivity(project, activity, occupiedEmployee);
//
//        List<Employee> actualAvailableEmployees = db.getEmployees();
//        actualAvailableEmployees.remove(occupiedEmployee);
//
//        assertThat(availableEmployees, equalTo(db.getEmployees()));
//    }
//
//    @Test
//    public void testStartDateInThePast() throws Exception {
//        expectedEx.expect(WrongDateException.class);
//        expectedEx.expectMessage("Start date is not allowed to be in the past!");
//
//        LocalDate startDate = LocalDate.parse("2016-04-10");
//        LocalDate endDate = LocalDate.parse("2016-05-15");
//
//        pms.findAvailableEmployees(startDate, endDate, activity);
//    }
//
//    @Test
//    public void testEndDateIsBeforeStartDate() throws Exception {
//        expectedEx.expect(WrongDateException.class);
//        expectedEx.expectMessage("End date is not allowed to be before start date!");
//
//        LocalDate startDate = LocalDate.parse("2016-05-15");
//        LocalDate endDate = LocalDate.parse("2016-05-10");
//
//        pms.findAvailableEmployees(startDate, endDate, activity);
//    }

}
