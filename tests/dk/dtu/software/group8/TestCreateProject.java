package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCreateProject {
	
	PManagementSystem pms;
	DatabaseManager db;
	
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    /**
     * Created by Tobias
     */
	@Before
	public void setup() throws IOException {
		pms = new PManagementSystem();
        db =  new DatabaseManager("Employees.txt");

        //Set current date to the may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        LocalDate date = LocalDate.parse("2016-05-09");
        when(pms.getDate()).thenReturn(date);
        
        //Login a user
        pms.signIn(db.getEmployees().get(0).getId());
        assertTrue(pms.userLoggedIn());

        //Check the project base is empty.
        assertThat(pms.getProjects().isEmpty(), is(true));
	}

    /**
     * Created by Marcus
     */
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

    /**
     * Created by Morten
     */
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

    /**
     * Created by Tobias
     */
    @Test //End date before start.
    public void createProjectC() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("End Date is not allowed to be before Start Date!");

        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-05-03");

        //Create the project
        Project project = pms.createProject(startDate, endDate);
        assertThat(project, is(nullValue()));
    }

    /**
     * Created by Marcus
     */
    @Test //Start date in the past, correct end date
    public void createProjectD() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");

        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-05-10");

        //Create the project
        Project project = pms.createProject(startDate, endDate);
        assertThat(project, is(nullValue()));
    }

    /**
     * Created by Morten
     */
    @Test //Incorrect start date, incorrect end date. (Both in past)
    public void createProjectE() throws WrongDateException, NoAccessException {
        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Start Date is not allowed to be in the past!");
        
        LocalDate startDate = LocalDate.parse("2016-04-25");
        LocalDate endDate = LocalDate.parse("2016-04-30");

        //Create the project
        Project project = pms.createProject(startDate, endDate);
        assertThat(project, is(nullValue()));
    }

    /**
     * Created by Tobias
     */
    @Test //No dates.
    public void createProjectF() throws WrongDateException, NoAccessException {

        //Check correct exception
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("Missing date(s)!");

        //Create the project with no dates.
        Project project = pms.createProject(null, null);
        assertThat(project, is(nullValue()));
    }
}