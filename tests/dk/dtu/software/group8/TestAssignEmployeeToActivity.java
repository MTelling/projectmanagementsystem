package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Morten on 11/04/16.
 */
public class TestAssignEmployeeToActivity extends TestManageProject {

//    Activity activity;
//
//    @Before
//    public void setupActivity() {
//        activity = project.createActivity("Implementation", 37,42,42);
//
//        assertThat(activity, is(not(null)));
//    }
//
//    @Test //Correct username, employee is available
//    public void testA() {
//        activity.addEmployee(pms.getCurrentEmployee());
//
//        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
//    }
//
//    @Test //Correct username, employee unavailable
//    public void testB() throws TooManyActivitiesException{
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
//    @Test //Incorrect username
//    public void testC() {
//        expectedEx.expect(InvalidEmployeeException.class);
//        expectedEx.expectMessage("No employee with that name is in the system.");
//
//        String empName = "ImNotAnEmployee";
//        Employee emp = pms.getEmployeeFromName(empName);
//    }
//
//    @Test //Not project manager, but otherwise correct.
//    public void testD() {
//        expectedEx.expect(NoAccessException.class);
//        expectedEx.expectMessage("Current user is not Project Manager for this project.");
//
//        //Sign in as employee who is not PM.
//        pms.signIn(db.getEmployees()[2]);
//
//        //Run test A again.
//        testA();
//    }

}
