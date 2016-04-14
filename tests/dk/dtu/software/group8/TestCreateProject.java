package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

public class TestCreateProject {
	
	PManagementSystem pms;
	DatabaseManager db = new DatabaseManager();
	
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		pms = new PManagementSystem();

        //Set current date to the may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        LocalDate date = LocalDate.parse("2016-05-09");
        when(pms.getDate()).thenReturn(date);
        
        //Login a user
        pms.signIn(db.getEmployees()[0]);
        assertThat(pms.userLoggedIn(), is(true));

        //Check the project base is empty.
        assertThat(pms.getProjects().isEmpty(), is(true));
	}



	@Test //Correct start and end.
	public void createProjectA() throws WrongDateException, NoAccessException {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");
        
        //Create the project
		Project project = pms.createProject(startDate, endDate);

        //Check that the project was added.
		assertThat(pms.getProjects().size(), is(1));

        //Test that date is set correct.
        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));

        assertThat(pms.getProjects().get(0), instanceOf(Project.class));
		assertThat(pms.getProjects().get(0).getId(), RegexMatcher.matches("^[0-9]{1,6}$"));
	}


    @Test //Correct start and end.
    public void createProjectB() throws WrongDateException, NoAccessException {
    	LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        //Create the project
        Project project = pms.createProject(startDate, endDate);

        //Check that the project was added.
        assertThat(pms.getProjects().size(), is(1));

        //Test that date is set correct.
        assertThat(project.getStartDate(), is(startDate));
        assertThat(project.getEndDate(), is(endDate));

        assertThat(pms.getProjects().get(0), instanceOf(Project.class));
		assertThat(pms.getProjects().get(0).getId(), RegexMatcher.matches("^[0-9]{1,6}$"));
    }

    @Test //End date before start.
    public void createProjectC() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End date is before start date.");

        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-03");

        //Create the project
        Project project = pms.createProject(startDate, endDate);
        assertThat(project, is(nullValue()));
    }

    @Test //Start date in the past, correct end date
    public void createProjectD() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        //Create the project
        Project project = pms.createProject(startDate, endDate);
        assertThat(project, is(nullValue()));
    }

    @Test //Incorrect start date, incorrect end date. (Both in past)
    public void createProjectE() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Date is in the past.");
        
        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-04-30");

        //Create the project
        Project project = pms.createProject(startDate, endDate);
        assertThat(project, is(nullValue()));
    }

    @Test //No dates.
    public void createProjectF() throws WrongDateException, NoAccessException {

        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Missing date(s).");

        //Create the project with no dates.
        Project project = pms.createProject(null, null);
        assertThat(project, is(nullValue()));
    }
}
