package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;


/**
 * Created by Morten on 11/04/16.
 */
public class TestChangeProjectDates extends TestManageProject{


    @Test
    public void testCorrectStartAndEnd() {
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);

        project.setStartDate(startDate);
        project.setEndDate(endDate);

        assertEquals(project.getStartDate(), startDate);
        assertEquals(project.getEndDate(), endDate);
    }


    @Test
    public void testCorrectStartAndSameEnd() {
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 10);

        project.setStartDate(startDate);
        project.setEndDate(endDate);

        assertEquals(project.getStartDate(), startDate);
        assertEquals(project.getEndDate(), endDate);
    }

    @Test
    public void testCorrectStartIncorrectEnd() throws WrongDateException{
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End date is before start date.");

        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 3);

        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    @Test
    public void testIncorrectStartCorrectEnd() throws WrongDateException{
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");

        Calendar startDate = new GregorianCalendar(2016, Calendar.APRIL, 25);
        Calendar endDate = new GregorianCalendar(2016, Calendar.MAY, 10);

        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    @Test
    public void testIncorrectStartIncorrectEnd() throws WrongDateException{
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");

        Calendar startDate = new GregorianCalendar(2016, Calendar.APRIL, 25);
        Calendar endDate = new GregorianCalendar(2016, Calendar.APRIL, 30);

        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    @Test
    public void testNoEndOrStart() throws WrongDateException{
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Missing date(s).");

        //Create the project with no dates.
        Project project = pms.createProject(null, null);
    }

}
