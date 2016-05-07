package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Tobias
 */
public class TestAssignEmployeeToActivity extends TestManageProject {

    ProjectActivity activity;
    YearWeek week21 = new YearWeek(2016, 21);
    YearWeek week22 = new YearWeek(2016, 22);

    /**
     * Created by Morten
     */
    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException, WrongDateException {
        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42);
        assertThat(activity, is(not(nullValue())));
    }

    /**
     * Created by Tobias
     */
    @Test //Correct username, employee is available
    public void testA() throws NoAccessException, TooManyActivitiesException,
            EmployeeAlreadyAddedException, NullNotAllowed {
        pms.addEmployeeToActivity(activity, pms.getCurrentEmployee());

        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
    }

    /**
     * Created by Marcus
     */
    @Test //Correct username, employee unavailable
    public void testB() throws TooManyActivitiesException, NoAccessException,
            IncorrectAttributeException, EmployeeAlreadyAddedException, WrongDateException {
        expectedEx.expect(TooManyActivitiesException.class);
        expectedEx.expectMessage("Employee is assigned to too many activities in given period.");

        //Try to add the employee to 20 activities.
        for (int i = 0; i < 21; i++) {
        	activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42);
            activity.addEmployee(pms.getCurrentEmployee());
        }
    }

    /**
     * Created by Morten
     */
    @Test //Incorrect username
    public void testC() throws InvalidEmployeeException {
        expectedEx.expect(InvalidEmployeeException.class);
        expectedEx.expectMessage("No employee with that name is in the system.");

        String empName = "ImNotAnEmployee";

        Employee emp = pms.getEmployeeFromId(empName);
    }

    /**
     * Created by Tobias
     */
    @Test //Not project manager, but otherwise correct.
    public void testD() throws NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(pms.getEmployees().get(2).getId());

        //Run test A again.
        testA();
    }

    /**
     * Created by Marcus
     */
    @Test //Add the same employee twice
    public void testE() throws NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed {
        expectedEx.expect(EmployeeAlreadyAddedException.class);
        expectedEx.expectMessage("That employee has already been assigned to the activity.");

        //Running test A twice should do this.
        testA();
        testA();
    }

    /**
     * Created by Morten
     */
    @Test
    public void testAddEmpAsEmployeeIfAlreadyAssignedAsConsultant() throws Exception {
        Employee emp = pms.getEmployeeFromId("toli");

        //Add current employee to activity so he can add consultants
        pms.addEmployeeToActivity(activity, pms.getCurrentEmployee());

        //Add emp as consultant
        pms.addEmployeeToActivityAsConsultant(activity, emp);

        //Check that he has been set as consultant.
        assertThat(activity.getConsultants(), hasItem(emp));

        //Add employee to activity
        pms.addEmployeeToActivity(activity, emp);

        //Ensure that he is no longer a consultant, but is an employee now.
        assertThat(activity.getEmployees(), hasItem(emp));
        assertThat(activity.getConsultants(), not(hasItem(emp)));
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testAddNull() throws TooManyActivitiesException, NoAccessException,
            EmployeeAlreadyAddedException, NullNotAllowed {
        expectedEx.expect(NullNotAllowed.class);
        expectedEx.expectMessage("You need to choose an employee.");

        pms.addEmployeeToActivity(activity, null);
    }
}