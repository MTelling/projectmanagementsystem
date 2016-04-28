package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TestChangeActivityDates extends TestManageProject {

    Activity activity;

    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException{
        activity = pms.createActivityForProject(project, "Unit Testing", new YearWeek(2017, 37), new YearWeek(2017, 42), 42);
        assertThat(activity, instanceOf(ProjectActivity.class));
    }

    @Test
    public void testCorrectStartAndEnd() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        YearWeek newStart = new YearWeek(2016, 37);
        YearWeek newEnd = new YearWeek(2016, 42);

        pms.manageActivityDates(project, activity, newStart, newEnd);

        assertThat(activity.getStartWeek(), is(newStart));
        assertThat(activity.getEndWeek(), is(newEnd));
    }

    @Test
    public void testCorrectStartAndSameEnd() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        YearWeek newStart = new YearWeek(2016, 37);
        YearWeek newEnd = new YearWeek(2016, 37);

        pms.manageActivityDates(project, activity, newStart, newEnd);

        assertThat(activity.getStartWeek(), is(newStart));
        assertThat(activity.getEndWeek(), is(newEnd));
    }

    @Test
    public void testCorrectStartIncorrectEnd() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End Week is not allowed to be before Start Week!");

        YearWeek newStart = new YearWeek(2016, 42);
        YearWeek newEnd = new YearWeek(2016, 37);

        pms.manageActivityDates(project, activity, newStart, newEnd);
    }

    @Test
    public void testIncorrectStartCorrectEnd() throws WrongDateException,IncorrectAttributeException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Week is not allowed to be in the past!");

        YearWeek newStart = new YearWeek(2015, 37);
        YearWeek newEnd = new YearWeek(2016, 42);

        pms.manageActivityDates(project, activity, newStart, newEnd);
    }

    @Test
    public void testNoEndOrStart() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Week is not allowed to be in the past!");

        pms.manageActivityDates(project, activity, null, null);
    }

    @Test
    public void testNotManager() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(db.getEmployees().get(2).getId());

        //Try to change the date anyway.
        YearWeek newStart = new YearWeek(2016, 37);
        YearWeek newEnd = new YearWeek(2016, 42);

        pms.manageActivityDates(project, activity, newStart, newEnd);
    }

}
