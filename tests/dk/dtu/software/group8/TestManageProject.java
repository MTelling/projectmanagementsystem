package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

public class TestManageProject {

    PManagementSystem pms;
    Project project;
    Employee emp;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() throws WrongDateException, NoAccessException, AlreadyAssignedProjectManagerException {
        pms = new PManagementSystem();

        //Set current date to may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        LocalDate date = LocalDate.parse("2016-05-09");
        when(pms.getDate()).thenReturn(date);

        //Login a user

        emp = pms.getEmployees().get(0);
        pms.signIn(emp.getId());
        assertTrue(pms.userLoggedIn());

        //Check the project base is empty.
        assertThat(pms.getProjects().isEmpty(), is(true));

        //Create a project
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");
        project = pms.createProject(startDate, endDate);

        //Check that project is really added
        assertThat(pms.getProjects(), hasItems(project));
        
        //Assign current employee as project manager..
        pms.assignManagerToProject(project);
    }


}
