package dk.dtu.software.group8;


import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class TestFindAvailableEmployees extends TestManageProject {

    Activity activity;

    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException {
        activity = pms.createActivityForProject(project, "Unit Testing", new YearWeek(2017, 37), new YearWeek(2017, 42), 42);
        assertThat(activity, instanceOf(ProjectActivity.class));
    }

    @Test
    public void testFindAvailableEmployees() {

    }

}
