package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.InvalidEmployeeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;

public class TestAssignEmployeeToActivity extends TestManageProject {

    ProjectActivity activity;

    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException{
        activity = project.createActivity("Implementation", 37,42,42, pms.getCurrentEmployee());
        assertThat(activity, is(not(nullValue())));
    }

    @Test //Correct username, employee is available
    public void testA() throws NoAccessException{
        project.addEmployeeToActivity(activity, pms.getCurrentEmployee());

        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
    }

//    @Test //Correct username, employee unavailable
//    public void testB() throws TooManyActivitiesException {
//        expectedEx.expect(TooManyActivitiesException.class);
//        expectedEx.expectMessage("Employee is assigned to too many activities in given period.");
//
//        //Try to add the employee to 20 activities.
//        for (int i = 0; i < 21; i++) {
//            activity = project.createActivity("Implementation", 37,42,42);
//            activity.addEmployee(pms.getCurrentEmployee());
//        }
//
//    }
//
//    //TODO: Should we really have this test? We have exactly the same in TestEndActivity.(Prob. others).
    @Test //Incorrect username
    public void testC() throws InvalidEmployeeException {
        expectedEx.expect(InvalidEmployeeException.class);
        expectedEx.expectMessage("No employee with that name is in the system.");

        String empName = "ImNotAnEmployee";
        Employee emp = pms.getEmployeeFromName(empName);
    }

    @Test //Not project manager, but otherwise correct.
    public void testD() throws NoAccessException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(db.getEmployees()[2]);

        //Run test A again.
        testA();
    }

}
