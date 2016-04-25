package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

import org.junit.Before;
import org.junit.Test;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.InvalidEmployeeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;

public class TestAssignEmployeeToActivity extends TestManageProject {

    ProjectActivity activity;
    YearWeek week37 = new YearWeek(2016, 37);
    YearWeek week42 = new YearWeek(2016, 42);

    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException{
        activity = pms.createActivityForProject(project, "Implementation", week37, week42, 42);
        assertThat(activity, is(not(nullValue())));
    }

    @Test //Correct username, employee is available
    public void testA() throws NoAccessException, TooManyActivitiesException{
        project.addEmployeeToActivity(activity, pms.getCurrentEmployee());

        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
    }

    @Test //Correct username, employee unavailable
    public void testB() throws TooManyActivitiesException, NoAccessException, IncorrectAttributeException {
        expectedEx.expect(TooManyActivitiesException.class);
        expectedEx.expectMessage("Employee is assigned to too many activities in given period.");

        //Try to add the employee to 20 activities.
        for (int i = 0; i < 21; i++) {
        	activity = pms.createActivityForProject(project, "Implementation", week37, week42, 42);
            activity.addEmployee(pms.getCurrentEmployee());
        }

    }
//
//    //TODO: Should we really have this test? We have exactly the same in TestEndActivity.(Prob. others).
    @Test //Incorrect username
    public void testC() throws InvalidEmployeeException {
        expectedEx.expect(InvalidEmployeeException.class);
        expectedEx.expectMessage("No employee with that name is in the system.");

        String empName = "ImNotAnEmployee";

        Employee emp = pms.getEmployeeFromId(empName);
    }

//    @Test //Not project manager, but otherwise correct.
//    public void testD() throws NoAccessException, TooManyActivitiesException {
//        expectedEx.expect(NoAccessException.class);
//        expectedEx.expectMessage("Current user is not Project Manager for this project.");
//
//        //Sign in as employee who is not PM.
//        pms.signIn(db.getEmployees().get(2).getId());
//
//        //Run test A again.
//        testA();
//    }

}
