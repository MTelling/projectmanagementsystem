package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.NoAccessException;
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

public class TestProjectManagerAssignment {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    PManagementSystem pms;
    DatabaseManager db;
    Project project;

    @Before
    public void setup() throws WrongDateException, NoAccessException {
        pms = new PManagementSystem();
        db = new DatabaseManager();

        //Set current date to the may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        Calendar cal = new GregorianCalendar(2016, Calendar.MAY,9);
        when(pms.getDate()).thenReturn(cal);

        //Login a user
        pms.signIn(db.getEmployees()[0]);
        assertTrue(pms.userLoggedIn());

        //Check the project base is empty.
        assertTrue(pms.getProjects().isEmpty());

        //Create a project
        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);
        project = pms.createProject(startDate, endDate);

        assertEquals(pms.getProjects().size(), 1);
    }

//    @Test
//    public void testIsEmployee() {
//        String empName = db.getEmployees()[2];
//        Employee emp = pms.getEmployeeFromName(empName);
//
//        project.assignProjectManager(emp);
//        assertEquals(project.getProjectManager(), emp);
//    }
//
//    @Test
//    public void testIsNotEmployee() throws InvalidEmployeeException {
//        expectedEx.expect(InvalidEmployeeException.class);
//        expectedEx.expectMessage("No employee with that name is in the system.");
//
//        String empName = "ImNotAnEmployee";
//        Employee emp = pms.getEmployeeFromName(empName);
//    }
//
//    @Test
//    public void testProjectHasManager() throws AlreadyAssignedProjectManagerException{
//        expectedEx.expect(AlreadyAssignedProjectManagerException.class);
//        expectedEx.expectMessage("The project already has a Project Manager.");
//
//        String empName = db.getEmployees()[2];
//        Employee emp = pms.getEmployeeFromName(empName);
//
//        project.assignProjectManager(emp);
//        assertEquals(project.getProjectManager(), emp);
//
//        String secondEmpName = db.getEmployees()[0];
//        Employee secondEmp = pms.getEmployeeFromName(secondEmpName);
//
//        project.assignProjectManager(secondEmp);
//    }
}
