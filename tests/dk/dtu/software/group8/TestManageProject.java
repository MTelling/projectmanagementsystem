package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestManageProject {

    PManagementSystem pms;
    DatabaseManager db;
    Project project;
    String emp;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() throws WrongDateException {
        pms = new PManagementSystem();
        db = new DatabaseManager();

        //Set current date to the may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        Calendar cal = new GregorianCalendar(2016, Calendar.MAY,9);
        when(pms.getDate()).thenReturn(cal);

        //Login a user
        emp = db.getEmployees()[0];
        pms.signIn(emp);
        assertTrue(pms.userLoggedIn());

        //Check the project base is empty.
        //TODO: could we do this smarter?
        assertEquals(pms.getProjects().size(), 0);

        //Create a project
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);
        project = pms.createProject(startDate, endDate);

        //Assign current employee as project manager.
        project.assignProjectManager(new Employee(emp));

        assertEquals(pms.getProjects().size(), 1);
    }


}
