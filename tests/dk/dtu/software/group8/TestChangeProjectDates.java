package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.mockito.cglib.core.Local;

public class TestChangeProjectDates extends TestManageProject{


    @Test
    public void testCorrectStartAndEnd() throws WrongDateException, NoAccessException{
    	LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        pms.manageProjectDates(project, startDate, endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));
    }

    @Test
    public void testCorrectStartAndSameEnd() throws WrongDateException, NoAccessException{
    	LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        pms.manageProjectDates(project, startDate, endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));
    }

    @Test
    public void testCorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End Date is not allowed to be before Start Date!");

        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-03");

        pms.manageProjectDates(project, startDate, endDate);
    }

    @Test
    public void testIncorrectStartCorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        pms.manageProjectDates(project, startDate, endDate);
    }

    @Test
    public void testIncorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-04-30");

        pms.manageProjectDates(project, startDate, endDate);
    }

    @Test
    public void testNoEndOrStart() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");

        pms.manageProjectDates(project, null, null);
    }

    @Test
    public void testNotManager() throws WrongDateException, NoAccessException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(db.getEmployees().get(2).getId());

        //Try to change the date anyway.
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        pms.manageProjectDates(project, startDate, endDate);
    }

    @Test
    public void testStartDateSetToToday() throws WrongDateException, NoAccessException {

        LocalDate startDate = LocalDate.parse("2016-05-09");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        pms.manageProjectDates(project, startDate, endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));


    }

}