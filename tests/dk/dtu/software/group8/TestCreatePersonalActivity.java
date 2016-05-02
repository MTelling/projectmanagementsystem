package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCreatePersonalActivity {

    PManagementSystem pms;
    DatabaseManager db;
    Employee emp;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        pms = new PManagementSystem();
        db = new DatabaseManager();

        //Set current date to may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        LocalDate date = LocalDate.parse("2016-05-09");
        when(pms.getDate()).thenReturn(date);

        //Login a user
        emp = db.getEmployees().get(0);
        pms.signIn(emp.getId());
        assertThat(pms.userLoggedIn(), is(true));
    }

    @Test
    public void testAllValid() throws Exception {
        assertThat(emp.getPersonalActivities().isEmpty(), is(true));

        String activityType = "Vacation";
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-31");

        PersonalActivity personalActivity = pms.createPersonalActivityForEmployee(activityType, startDate, endDate, emp);

        //Make sure the personal activity was actually added to the employee.
        assertThat(emp.getPersonalActivities(), hasItem(personalActivity));
    }

    @Test
    public void testInvalidActivityType() throws Exception{
        expectedEx.expect(IncorrectAttributeException.class);
        expectedEx.expectMessage("The supplied activity type is not a correct activity type!");

        String activityType = "JohnJohn123";
        LocalDate startDate = LocalDate.parse("2016-04-10");
        LocalDate endDate = LocalDate.parse("2016-05-31");
        pms.createPersonalActivityForEmployee(activityType, startDate, endDate, emp);
    }

    @Test
    public void testStartDateInPast() throws Exception{
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("The supplied time period is not a legal time period (Start before now or end before start)!");

        String activityType = "Vacation";
        LocalDate startDate = LocalDate.parse("2016-04-10");
        LocalDate endDate = LocalDate.parse("2016-05-31");

        pms.createPersonalActivityForEmployee(activityType, startDate, endDate, emp);
    }

    @Test
    public void testEndDateBeforeStartDate() throws Exception{
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("The supplied time period is not a legal time period (Start before now or end before start)!");

        String activityType = "Vacation";
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-04-30");

        pms.createPersonalActivityForEmployee(activityType, startDate, endDate, emp);
    }

    @Test
    public void testBothDatesInPast() throws Exception{
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("The supplied time period is not a legal time period (Start before now or end before start)!");

        String activityType = "Vacation";
        LocalDate startDate = LocalDate.parse("2016-04-10");
        LocalDate endDate = LocalDate.parse("2016-04-30");

        pms.createPersonalActivityForEmployee(activityType, startDate, endDate, emp);
    }

    @Test
    public void testTimeperiodOccupiedByOtherPA() throws Exception {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Time period is already occupied by another personal activity!");

        String activityType = "Vacation";
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-31");

        PersonalActivity personalActivity1 = pms.createPersonalActivityForEmployee(activityType, startDate, endDate, emp);

        assertThat(emp.getPersonalActivities(), hasItem(personalActivity1));

        pms.createPersonalActivityForEmployee("Courses", LocalDate.parse("2016-05-11"), LocalDate.parse("2016-05-28"), emp);
    }
}
