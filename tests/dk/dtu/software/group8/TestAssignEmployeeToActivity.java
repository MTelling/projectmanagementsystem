package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

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
    public void testA() throws NoAccessException, TooManyActivitiesException,
            EmployeeAlreadyAddedException, NullNotAllowed {
        pms.addEmployeeToActivity(project, activity, pms.getCurrentEmployee());

        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
    }

    @Test //Correct username, employee unavailable
    public void testB() throws TooManyActivitiesException, NoAccessException,
            IncorrectAttributeException, EmployeeAlreadyAddedException {
        expectedEx.expect(TooManyActivitiesException.class);
        expectedEx.expectMessage("Employee is assigned to too many activities in given period.");

        //Try to add the employee to 20 activities.
        for (int i = 0; i < 21; i++) {
        	activity = pms.createActivityForProject(project, "Implementation", week37, week42, 42);
            activity.addEmployee(pms.getCurrentEmployee());
        }

    }

    //TODO: Should we really have this test? We have exactly the same in TestEndActivity.(Prob. others).
    @Test //Incorrect username
    public void testC() throws InvalidEmployeeException {
        expectedEx.expect(InvalidEmployeeException.class);
        expectedEx.expectMessage("No employee with that name is in the system.");

        String empName = "ImNotAnEmployee";

        Employee emp = pms.getEmployeeFromId(empName);
    }

    @Test //Not project manager, but otherwise correct.
    public void testD() throws NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(pms.getEmployees().get(2).getId());

        //Run test A again.
        testA();
    }


    @Test //Add the same employee twice
    public void testE() throws NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed {
        expectedEx.expect(EmployeeAlreadyAddedException.class);
        expectedEx.expectMessage("That employee has already been assigned to the activity.");

        //Running test A twice should do this.
        testA();
        testA();
    }


    @Test
    public void testAddEmpAsEmployeeIfAlreadyAssignedAsConsultant() throws Exception {
        Employee emp = pms.getEmployeeFromId("toli");

        //Add current employee to activity so he can add consultants
        pms.addEmployeeToActivity(project, activity, pms.getCurrentEmployee());

        //Add emp as consultant
        pms.addEmployeeToActivityAsConsultant(activity, emp);

        //Check that he has been set as consultant.
        assertThat(activity.getConsultants(), hasItem(emp));

        //Add employee to activity
        pms.addEmployeeToActivity(project, activity, emp);

        //Ensure that he is no longer a consultant, but is an employee now.
        assertThat(activity.getEmployees(), hasItem(emp));
        assertThat(activity.getConsultants(), not(hasItem(emp)));
    }

    @Test
    public void testAddNull() throws TooManyActivitiesException, NoAccessException,
            EmployeeAlreadyAddedException, NullNotAllowed {
        expectedEx.expect(NullNotAllowed.class);
        expectedEx.expectMessage("You need to choose an employee.");

        pms.addEmployeeToActivity(project, activity, null);

    }



}
