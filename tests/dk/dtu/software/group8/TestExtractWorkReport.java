package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.EmployeeAlreadyAddedException;
import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestExtractWorkReport  extends TestManageProject {


    ProjectActivity activity;
    YearWeek week37 = new YearWeek(2016, 37);
    YearWeek week42 = new YearWeek(2016, 42);


    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException {
        activity = pms.createActivityForProject(project, "Implementation", week37, week42, 42);
        assertThat(activity, is(not(nullValue())));
        pms.addEmployeeToActivity(project, activity, pms.getCurrentEmployee());
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
    }

    @Test
    public void extractReport() throws IOException {
        WorkReport DankReport = new WorkReport(project);
        DankReport.make();

    }

}
