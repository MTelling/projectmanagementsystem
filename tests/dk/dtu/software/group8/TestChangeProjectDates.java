package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

public class TestChangeProjectDates extends TestManageProject{


    @Test
    public void testCorrectStartAndEnd() throws WrongDateException, NoAccessException{
    	LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        project.setStartDate(startDate);
        project.setEndDate(endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));
    }

    @Test
    public void testCorrectStartAndSameEnd() throws WrongDateException, NoAccessException{
    	LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        project.setStartDate(startDate);
        project.setEndDate(endDate);

        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));
    }

    @Test
    public void testCorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End date is before start date.");

        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-03");

        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    @Test
    public void testIncorrectStartCorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    @Test
    public void testIncorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-04-30");

        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    @Test
    public void testNoEndOrStart() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Missing date(s).");

        //Create the project with no dates.
        Project project = pms.createProject(null, null);
        assertThat(project, is(nullValue()));
    }

    @Test
    public void testNotManager() throws WrongDateException, NoAccessException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(db.getEmployees()[2]);

        //Try to change the date anyway.
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

}