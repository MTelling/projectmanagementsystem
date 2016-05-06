package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NegativeHoursException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestChangeActivityDates extends TestManageProject {

    ProjectActivity activity;

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

        assertThat(activity.getStartDate(), is(newStart.toLocalDate()));
        assertThat(activity.getEndDate(), is(newEnd.toLocalDate()));
    }

    @Test
    public void testCorrectStartAndSameEnd() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        YearWeek newStart = new YearWeek(2016, 37);
        YearWeek newEnd = new YearWeek(2016, 37);

        pms.manageActivityDates(project, activity, newStart, newEnd);

        assertThat(activity.getStartDate(), is(newStart.toLocalDate()));
        assertThat(activity.getEndDate(), is(newEnd.toLocalDate()));
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
        pms.signIn(pms.getEmployees().get(2).getId());

        //Try to change the date anyway.
        YearWeek newStart = new YearWeek(2016, 37);
        YearWeek newEnd = new YearWeek(2016, 42);

        pms.manageActivityDates(project, activity, newStart, newEnd);
    }

    @Test
    public void testStartDateIsToday() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        YearWeek newStart = YearWeek.fromDate(LocalDate.parse("2016-05-09"));
        YearWeek newEnd = YearWeek.fromDate(LocalDate.parse("2016-05-15"));

        pms.manageActivityDates(project,activity, newStart, newEnd);
    }


    //TODO: Should we add these two extra tests?
    @Test
    public void testEndDateExceedsProject() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("The given end week exceeds the duration of the project.");

        YearWeek newStart = YearWeek.fromDate(LocalDate.parse("2016-05-12"));
        YearWeek newEnd = YearWeek.fromDate(LocalDate.parse("2020-05-15"));

        assertTrue(newEnd.isAfter(YearWeek.fromDate(project.getEndDate())));

        pms.manageActivityDates(project, activity, newStart, newEnd);

    }

    @Test
    public void testStartDateIsBeforeProject() throws WrongDateException, IncorrectAttributeException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("The given start week is before the start of the project.");

        LocalDate newProjectStart = LocalDate.parse("2016-07-10");
        LocalDate newProjectEnd = LocalDate.parse("2016-09-12");

        pms.manageProjectDates(project, newProjectStart, newProjectEnd);

        YearWeek newStart = YearWeek.fromDate(LocalDate.parse("2016-05-12"));
        YearWeek newEnd = YearWeek.fromDate(LocalDate.parse("2020-08-15"));

        assertThat(newStart.isBefore(YearWeek.fromDate(project.getStartDate())), is(true));

        pms.manageActivityDates(project, activity, newStart, newEnd);

    }

    @Test
    public void testChangeExpectedHours() throws Exception {
        pms.changeActivityApproximatedHours(project, activity, 84);
        assertThat(activity.getApproximatedHours(), is(84));
    }

    @Test
    public void testChangeApproximatedHoursNegative() throws Exception {
        expectedEx.expect(NegativeHoursException.class);
        expectedEx.expectMessage("Approximated hours can not be negative!");

        pms.changeActivityApproximatedHours(project, activity, -42);
        assertThat(activity.getApproximatedHours(), is(42));
    }
}
