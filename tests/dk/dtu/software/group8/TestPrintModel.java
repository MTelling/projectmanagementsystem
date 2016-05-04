package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Morten on 04/05/16.
 */
public class TestPrintModel extends TestCreateProject {

    //TODO: tests in here run the create project tests as well?
    private Project project;

    @Before
    public void setupProject() throws WrongDateException {
        super.setup();
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");

        //Create the project
        project = new Project("160000", startDate, endDate);
        project.assignProjectManager(pms.getCurrentEmployee());
    }

    @Test
    public void testProjectToString() throws WrongDateException {

        String projectStr = project.toString();

        assertThat(projectStr, is("160000\t - \tN/A (2016-05-10 - 2016-06-10)"));
    }

    @Test
    public void testProjectActivityToString() throws WrongDateException, NoAccessException, IncorrectAttributeException {

        ProjectActivity activity = pms.createActivityForProject(project,
                "Implementation",
                YearWeek.fromDate(LocalDate.parse("2016-05-10")),
                YearWeek.fromDate(LocalDate.parse("2016-06-09")),
                40);


        String activityStr = activity.toString();
        System.out.println(activityStr);
        //TODO: Right now this prints a date?
        assertThat(activityStr, is (""));
    }

    @Test
    public void testEmployeeToString() {
        String empStr = pms.getCurrentEmployee().toString();

        assertThat(empStr, is("Hubert Baumeister"));
    }



}
