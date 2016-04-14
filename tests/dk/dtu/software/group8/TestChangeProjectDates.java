package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class TestChangeProjectDates extends TestManageProject{


    @Test
    public void testCorrectStartAndEnd() throws WrongDateException, NoAccessException{
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);

        project.setStartDate(startDate, pms.getCurrentEmployee());
        project.setEndDate(endDate, pms.getCurrentEmployee());

        assertEquals(project.getStartDate(), startDate);
        assertEquals(project.getEndDate(), endDate);
    }

    @Test
    public void testCorrectStartAndSameEnd() throws WrongDateException, NoAccessException{
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 10);

        project.setStartDate(startDate, pms.getCurrentEmployee());
        project.setEndDate(endDate, pms.getCurrentEmployee());

        assertEquals(project.getStartDate(), startDate);
        assertEquals(project.getEndDate(), endDate);
    }

    @Test
    public void testCorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End date is before start date.");

        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 3);

        project.setStartDate(startDate, pms.getCurrentEmployee());
        project.setEndDate(endDate, pms.getCurrentEmployee());
    }

    @Test
    public void testIncorrectStartCorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");

        Calendar startDate = new GregorianCalendar(2016, Calendar.APRIL, 25);
        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 10);

        project.setStartDate(startDate, pms.getCurrentEmployee());
        project.setEndDate(endDate, pms.getCurrentEmployee());
    }

    @Test
    public void testIncorrectStartIncorrectEnd() throws WrongDateException, NoAccessException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");

        Calendar startDate = new GregorianCalendar(2016, Calendar.APRIL, 25);
        Calendar endDate = new GregorianCalendar(2016, Calendar.APRIL, 30);

        project.setStartDate(startDate, pms.getCurrentEmployee());
        project.setEndDate(endDate, pms.getCurrentEmployee());
    }

    @Test
    public void testNoEndOrStart() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Missing date(s).");

        //Create the project with no dates.
        Project project = pms.createProject(null, null);
    }

    @Test
    public void testNotManager() throws WrongDateException, NoAccessException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        pms.signIn(db.getEmployees().get(2).getId());

        //Try to change the date anyway.
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);

        project.setStartDate(startDate, pms.getCurrentEmployee());
        project.setEndDate(endDate, pms.getCurrentEmployee());
    }

}