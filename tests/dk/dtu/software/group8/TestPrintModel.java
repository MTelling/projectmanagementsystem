package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Morten on 04/05/16.
 */
public class TestPrintModel {

    private Project project;

    PManagementSystem pms;
    DatabaseManager db;


    @Before
    public void setup() throws InvalidNameException, IOException, WrongDateException {
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

        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2018-06-10");

        //Create the project
        project = new Project("160000", startDate, endDate);
        project.assignProjectManager(pms.getCurrentEmployee());
        project.setName("SoftwareEngineering");
    }

    @Test
    public void testProjectToString() throws WrongDateException {

        String projectStr = project.toString();

        assertThat(projectStr, is("160000\t - \tSoftwareEngineering (2016-05-10 - 2018-06-10)"));
    }

    @Test
    public void testProjectNoNameToString() throws WrongDateException {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2018-06-10");
        Project noNameProject = new Project("160000", startDate, endDate);

        String projectStr = noNameProject.toString();

        assertThat(projectStr, is("160000\t - \tN/A (2016-05-10 - 2018-06-10)"));
    }

    @Test
    public void testProjectActivityToString() throws WrongDateException, NoAccessException, IncorrectAttributeException {

        YearWeek startWeek = new YearWeek(2016,37);
        YearWeek endWeek = new YearWeek(2016,40);

        ProjectActivity activity = pms.createActivityForProject(project,
                "Implementation",
                startWeek,
                endWeek,
                40);


        String activityStr = activity.toString();

        assertThat(activityStr, is ("Implementation (week 37.2016 - week 40.2016) from project: SoftwareEngineering"));
    }

    @Test
    public void testEmployeeToString() {
        String empStr = pms.getCurrentEmployee().toString();

        assertThat(empStr, is("Hubert Baumeister"));
    }

    @Test
    public void testPersonalActivityToString() throws IncorrectAttributeException, WrongDateException {
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2018-06-10");

        PersonalActivity act = pms.createPersonalActivityForEmployee("Test",startDate,endDate,pms.getCurrentEmployee());

        String personalActivityStr = act.toString();
        assertThat(personalActivityStr, is("Test (2016-05-10 - 2018-06-10)"));
    }



}
