package dk.dtu.software.group8;

import com.sun.media.sound.InvalidDataException;
import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tobias
 */
public class TestCreateProjectActivity extends TestManageProject {
	
	LocalDate week36, week37, week42;

	/**
	 * Created by Marcus
	 */
	@Before
	public void setUpWeeks() {

		week36 = new YearWeek(2016, 36).toLocalDate();
		week37 = new YearWeek(2016, 37).toLocalDate();
		week42 = new YearWeek(2016, 42).toLocalDate();
	}

	/**
	 * Created by Morten
	 */
	@Test
	public void testCreateNewProjectActivitySuccess() throws IncorrectAttributeException {
		String activityType = "Implementation";
		int approximatedHours = 42;

		ProjectActivity projectActivity = new ProjectActivity(activityType, week37, week42, approximatedHours, project);

		assertThat(projectActivity, instanceOf(Activity.class));
		assertThat(projectActivity, instanceOf(ProjectActivity.class));

		assertThat(projectActivity.getActivityType(), is(activityType));
		assertThat(projectActivity.getStartDate(), is(week37));
		assertThat(projectActivity.getEndDate(), is(week42));
		assertThat(projectActivity.getApproximatedHours(), is(approximatedHours));
	}

	/**
	 * Created by Tobias
	 */
	@Test
	public void testCreateNewProjectActivityIncorrectActivityType() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied activity type is not a correct activity type.");

		String activityType = "1234";
		int approximatedHours = 42;

		Activity projectActivity = new ProjectActivity(activityType, week37, week42, approximatedHours, project);
		assertThat(projectActivity, is(nullValue()));
	}

	/**
	 * Created by Marcus
	 */
	@Test
	public void testCreateNewProjectActivityIncorrectTimePeriod() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied time period is not a legal time period (Start before now or end before start).");

		String activityType = "Implementation";
		int approximatedHours = 42;

		Activity projectActivity = new ProjectActivity(activityType, week37, week36, approximatedHours, project);
		assertThat(projectActivity, is(nullValue()));
	}

	/**
	 * Created by Morten
	 */
	@Test
	public void testCreateNewProjectActivityIncorrectAproximatedTime() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied approximated time is not allowed to be negative.");

		String activityType = "Implementation";
		int approximatedHours = -42;

		Activity projectActivity = new ProjectActivity(activityType, week37, week42, approximatedHours, project);
		assertThat(projectActivity, is(nullValue()));
	}

	/**
	 * Created by Tobias
	 */
	@Test
	public void testCreateProjectActivity() throws IncorrectAttributeException, NoAccessException, WrongDateException {
		assertThat(project.getProjectManager(), is(pms.getCurrentEmployee()));
		assertThat(project.getActivities().isEmpty(), is(true));

		int numOfActivities = project.getActivities().size();

		Activity projectActivity = pms.createActivityForProject(project, "Implementation", YearWeek.fromDate(week37), YearWeek.fromDate(week42), 42);

		assertThat(projectActivity, instanceOf(ProjectActivity.class));
		assertThat(project.getActivities().size(), is(numOfActivities + 1));
	}

	/**
	 * Created by Marcus
	 */
	@Test
	public void testCreateProjectActivityNotManager() throws IncorrectAttributeException, NoAccessException, WrongDateException {
		expectedEx.expect(NoAccessException.class);
		expectedEx.expectMessage("Current user is not Project Manager for this project.");

		//Sign in as employee who is not PM.
		pms.signIn(pms.getEmployees().get(2).getId());

		pms.createActivityForProject(project, "Implementation", YearWeek.fromDate(week37), YearWeek.fromDate(week42), 42);
	}

	/**
	 * Created by Morten
	 */
	@Test
    public void testCreateProjectActivityBeforeProject() throws NoAccessException, WrongDateException, IncorrectAttributeException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("The given start week is before the start of the project.");

        LocalDate newProjectStart = LocalDate.parse("2016-07-10");
        LocalDate newProjectEnd = LocalDate.parse("2016-09-12");

        pms.manageProjectDates(project, newProjectStart, newProjectEnd);

        assertThat(project.getStartDate(), is(newProjectStart));
        assertThat(project.getEndDate(), is(newProjectEnd));

        YearWeek startWeek = YearWeek.fromDate(LocalDate.parse("2016-05-12"));
        YearWeek endWeek = YearWeek.fromDate(LocalDate.parse("2016-08-15"));

        assertThat(startWeek.isBefore(YearWeek.fromDate(project.getStartDate())), is(true));

        pms.createActivityForProject(project, "Test", startWeek, endWeek, 40);
    }

	/**
	 * Created by Tobias
	 */
    @Test
    public void testCreateProjectActivityAfterProject() throws NoAccessException, IncorrectAttributeException, WrongDateException {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("The given end week exceeds the duration of the project.");

        YearWeek startWeek = YearWeek.fromDate(LocalDate.parse("2016-05-12"));
        YearWeek endWeek = YearWeek.fromDate(LocalDate.parse("2020-05-15"));

        assertTrue(endWeek.isAfter(YearWeek.fromDate(project.getEndDate())));

        pms.createActivityForProject(project, "Test", startWeek, endWeek, 40);
    }
    
    /**
     * Created by Morten
     */
    @Test
    public void testCreateProjectActivityNoManager() throws Exception {
    	expectedEx.expect(NoAccessException.class);
    	expectedEx.expectMessage("Current user is not Project Manager for this project.");
    	
    	Project p = pms.createProject(week37, week42);
    	
    	pms.createActivityForProject(p, "Implementation", new YearWeek(38,2016), new YearWeek(40,2016), 0);
    }
}