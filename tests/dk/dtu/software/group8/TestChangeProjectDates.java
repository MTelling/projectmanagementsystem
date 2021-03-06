package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Morten
 */
public class TestChangeProjectDates extends TestManageProject{

    /**
     * Created by Marcus
     */
    @Test
    public void testCorrectStartAndEnd() throws WrongDateException, NoAccessException{
    	LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        pms.manageProjectDates(project, startDate, endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));
    }

    /**
     * Created by Morten
     */
    @Test
    public void testCorrectStartAndSameEnd() throws WrongDateException, NoAccessException{
    	LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        pms.manageProjectDates(project, startDate, endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testCorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End Date is not allowed to be before Start Date!");

        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-03");

        pms.manageProjectDates(project, startDate, endDate);
    }

    /**
     * Created by Marcus
     */
    @Test
    public void testIncorrectStartCorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        pms.manageProjectDates(project, startDate, endDate);
    }

    /**
     * Created by Morten
     */
    @Test
    public void testIncorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-04-30");

        pms.manageProjectDates(project, startDate, endDate);
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testNoEndOrStart() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");

        pms.manageProjectDates(project, null, null);
    }

    /**
     * Created by Marcus
     */
    @Test
    public void testNotManager() throws WrongDateException, NoAccessException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(pms.getEmployees().get(2).getId());

        //Try to change the date anyway.
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        pms.manageProjectDates(project, startDate, endDate);
    }

    /**
     * Created by Morten
     */
    @Test
    public void testStartDateSetToToday() throws WrongDateException, NoAccessException {

        LocalDate startDate = LocalDate.parse("2016-05-09");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        pms.manageProjectDates(project, startDate, endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));
    }
}