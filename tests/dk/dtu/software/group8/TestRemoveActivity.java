package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.InvalidActivityException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestRemoveActivity extends TestManageProject {

    /**
     * Created by Marcus
     */
    @Test
    public void testRemoveProjectActivity() throws Exception {
        ProjectActivity projectActivity = pms.createActivityForProject(project, "Unit Testing", new YearWeek(2016, 21), new YearWeek(2016, 22), 42);
        assertThat(project.getActivities(), hasItem(projectActivity));

        pms.removeActivityFromProject(projectActivity);
        assertThat(project.getActivities(), not(hasItem(projectActivity)));
    }

    /**
     * Created by Morten
     */
    @Test
    public void testRemoveProjectActivityNotProjectManager() throws Exception {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        ProjectActivity projectActivity = pms.createActivityForProject(project, "Unit Testing", new YearWeek(2016, 21), new YearWeek(2016, 22), 42);
        assertThat(project.getActivities(), hasItem(projectActivity));

        //Log in as different employee
        pms.signIn(pms.getEmployees().get(1).getId());

        pms.removeActivityFromProject(projectActivity);
        assertThat(project.getActivities(), hasItem(projectActivity));
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testRemoveProjectActivityNotInProject() throws Exception {
        expectedEx.expect(InvalidActivityException.class);
        expectedEx.expectMessage("The activity does not belong to the project!");

        ProjectActivity projectActivity = new ProjectActivity("Unit Testing", new YearWeek(2016, 42).toLocalDate(), new YearWeek(2016, 45).toLocalDate(), 42, project);
        pms.removeActivityFromProject(projectActivity);
    }

    /**
     * Created by Marcus
     */
    @Test
    public void testRemovePersonalActivity() throws Exception {
        PersonalActivity personalActivity = pms.createPersonalActivityForEmployee("Vaction", LocalDate.parse("2016-07-21"), LocalDate.parse("2016-07-28"), emp);
        assertThat(emp.getPersonalActivities(), hasItem(personalActivity));

        pms.removePersonalActivity(personalActivity);
        assertThat(emp.getPersonalActivities(), not(hasItem(personalActivity)));
    }

    /**
     * Created by Morten
     */
    @Test
    public void testRemovePersonalActivityNotAssignedActivity() throws Exception {
        expectedEx.expect(InvalidActivityException.class);
        expectedEx.expectMessage("The activity does not belong to you!");

        PersonalActivity personalActivity = new PersonalActivity("Vaction", LocalDate.parse("2016-07-21"), LocalDate.parse("2016-07-28"));
        pms.removePersonalActivity(personalActivity);
    }
}