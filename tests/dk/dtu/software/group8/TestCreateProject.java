package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;

public class TestCreateProject {
	PManagementSystem pms;
	DatabaseManager db;
	DateServer dateServer;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		pms = new PManagementSystem();
		db = new DatabaseManager();
		dateServer = new DateServer();

        //TODO: Current day should be mocked to the ninth of may.

        //Should login a user.

        //Check the project base is empty.
        pms.signIn(db.getEmployees()[0]);
        assertEquals(pms.getProjects().size(), 0);
	}


	@Test //Correct start and end.
	public void createProjectA() {
        Calendar startDate = dateServer.getDate();
        startDate.set(2016, Calendar.MAY, 10);

        Calendar endDate = dateServer.getDate();
        endDate.set(2016, Calendar.JUNE, 10);

        //Create the project
		Project project = pms.createProject(startDate, endDate);

        //Check that the project was added.
		assertEquals(pms.getProjects().size(), 1);

        //Test that date is set correct.
        assertEquals(project.getStartDate(), startDate);
        assertEquals(project.getEndDate(), endDate);
	}


    @Test //Correct start and end.
    public void createProjectB() {
        Calendar startDate = dateServer.getDate();
        startDate.set(2016, Calendar.MAY, 10);

        Calendar endDate = dateServer.getDate();
        endDate.set(2016, Calendar.MAY, 10);

        //Create the project
        Project project = pms.createProject(startDate, endDate);

        //Check that the project was added.
        assertEquals(pms.getProjects().size(), 1);

        //Test that date is set correct.
        assertEquals(project.getStartDate(), startDate);
        assertEquals(project.getEndDate(), endDate);
    }

    @Test //End date before start.
    public void createProjectC() {
        Calendar startDate = dateServer.getDate();
        startDate.set(2016, Calendar.MAY, 10);

        Calendar endDate = dateServer.getDate();
        endDate.set(2016, Calendar.MAY, 3);

        //Create the project
        Project project = pms.createProject(startDate, endDate);

        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End date is before start date");

        //Check that the project was null.
        assertNull(project);

        //Check that the project wasn't added.
        assertEquals(pms.getProjects().size(), 0);
    }

    @Test //Start date in the past, correct end date
    public void createProjectD() {
        Calendar startDate = dateServer.getDate();
        startDate.set(2016, Calendar.APRIL, 25);

        Calendar endDate = dateServer.getDate();
        endDate.set(2016, Calendar.MAY, 10);

        //Create the project
        Project project = pms.createProject(startDate, endDate);

        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start date is in the past");

        //Check that the project was null.
        assertNull(project);

        //Check that the project wasn't added.
        assertEquals(pms.getProjects().size(), 0);
    }

    @Test //Incorrect start date, incorrect end date. (Both in past)
    public void createProjectE() {
        Calendar startDate = dateServer.getDate();
        startDate.set(2016, Calendar.APRIL, 25);

        Calendar endDate = dateServer.getDate();
        endDate.set(2016, Calendar.APRIL, 30);

        //Create the project
        Project project = pms.createProject(startDate, endDate);

        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Both start and end date is in the past.");

        //Check that the project was null.
        assertNull(project);

        //Check that the project wasn't added.
        assertEquals(pms.getProjects().size(), 0);
    }

    @Test //No dates.
    public void createProjectF() {

        //Create the project with no dates.
        Project project = pms.createProject(null, null);

        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("No dates were given.");

        //Check that the project was null.
        assertNull(project);

        //Check that the project wasn't added.
        assertEquals(pms.getProjects().size(), 0);
    }



    //These are not described in our test:
//	@Test
//	public void testForActualProject() {
//		pms.signIn(db.getEmployees()[0]);
//
//		pms.createProject();
//		assertThat(pms.getProjects()[0], instanceOf(Project.class));
//		assertThat(pms.getProjects()[0].getID(), RegexMatcher.matches("^[0-9]{1,6}$"));
//	}
}
