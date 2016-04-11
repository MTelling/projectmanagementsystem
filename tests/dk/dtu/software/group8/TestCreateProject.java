package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class TestCreateProject {
	PManagementSystem pms;
	DatabaseManager db;
	DateServer dateServer;

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


	@Test
	public void createProjectA() {
        //Make correct start date and end date.
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

    @Test
    public void createProjectC() {
        //Make correct start date and end date.
        Calendar startDate = dateServer.getDate();
        startDate.set(2016, Calendar.MAY, 10);

        Calendar endDate = dateServer.getDate();
        endDate.set(2016, Calendar.JUNE, 10);

        //Create the project
        Project project = pms.createProject(startDate, endDate);

        //Check that the project was null.
        assertNull(project);

        //Check that the project wasn't added.
        assertEquals(pms.getProjects().size(), 0);
    }




    //These are not described in our test:
//	@Test
//	public void createProjectNotSignedIn() {
//		assertEquals(pms.getProjects().size(), 0);
//		pms.createProject();
//		assertEquals(pms.getProjects().size(), 0);
//	}
//
//	@Test
//	public void testForActualProject() {
//		pms.signIn(db.getEmployees()[0]);
//
//		pms.createProject();
//		assertThat(pms.getProjects()[0], instanceOf(Project.class));
//		assertThat(pms.getProjects()[0].getID(), RegexMatcher.matches("^[0-9]{1,6}$"));
//	}
}
