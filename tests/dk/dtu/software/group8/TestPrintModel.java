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

/**
 * Created by Morten on 04/05/16.
 */
public class TestPrintModel extends TestCreateProject {

    //TODO: tests in here run the create project tests as well?
    private Project project;

    @Before
    public void setupProject() throws WrongDateException, InvalidNameException, IOException {
        super.setup();
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



}
