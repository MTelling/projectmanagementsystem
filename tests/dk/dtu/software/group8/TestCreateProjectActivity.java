package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

import org.junit.Before;
import org.junit.Test;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;

public class TestCreateProjectActivity extends TestManageProject {
	
	LocalDate week36, week37, week42;
	
	@Before
	public void setUpWeeks() {
		week36 = new YearWeek(2016, 36).toLocalDate();
		week37 = new YearWeek(2016, 37).toLocalDate();
		week42 = new YearWeek(2016, 42).toLocalDate();
	}
	
	@Test
	public void testCreateNewProjectActivitySuccess() throws IncorrectAttributeException {
		String activityType = "Implementation";
		int approximatedHours = 42;

		ProjectActivity projectActivity = new ProjectActivity(activityType, week37, week42, approximatedHours);

		assertThat(projectActivity, instanceOf(Activity.class));
		assertThat(projectActivity, instanceOf(ProjectActivity.class));

		assertThat(projectActivity.getActivityType(), is(activityType));
		assertThat(projectActivity.getStartDate(), is(week37));
		assertThat(projectActivity.getEndDate(), is(week42));
		assertThat(projectActivity.getApproximatedHours(), is(approximatedHours));
	}

	@Test
	public void testCreateNewProjectActivityIncorrectActivityType() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied activity type is not a correct activity type.");

		String activityType = "1234";
		int approximatedHours = 42;

		Activity projectActivity = new ProjectActivity(activityType, week37, week42, approximatedHours);
		assertThat(projectActivity, is(nullValue()));
	}

	@Test
	public void testCreateNewProjectActivityIncorrectTimePeriod() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied time period is not a legal time period (Start before now or end before start).");

		String activityType = "Implementation";
		int approximatedHours = 42;

		Activity projectActivity = new ProjectActivity(activityType, week37, week36, approximatedHours);
		assertThat(projectActivity, is(nullValue()));
	}

	@Test
	public void testCreateNewProjectActivityIncorrectAproximatedTime() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied approximated time is not allowed to be negative.");

		String activityType = "Implementation";
		int approximatedHours = -42;

		Activity projectActivity = new ProjectActivity(activityType, week37, week42, approximatedHours);
		assertThat(projectActivity, is(nullValue()));
	}

	@Test
	public void testCreateProjectActivity() throws IncorrectAttributeException, NoAccessException{
		assertThat(project.getProjectManager(), is(pms.getCurrentEmployee()));
		assertThat(project.getActivities().isEmpty(), is(true));

		int numOfActivities = project.getActivities().size();

		Activity projectActivity = pms.createActivityForProject(project, "Implementation", YearWeek.fromDate(week37), YearWeek.fromDate(week42), 42);

		assertThat(projectActivity, instanceOf(ProjectActivity.class));
		assertThat(project.getActivities().size(), is(numOfActivities + 1));
	}
	
	@Test
	public void testCreateProjectActivityNotManager() throws IncorrectAttributeException, NoAccessException {
		expectedEx.expect(NoAccessException.class);
		expectedEx.expectMessage("Current user is not Project Manager for this project.");

		//Sign in as employee who is not PM.
		pms.signIn(db.getEmployees().get(2).getId());

		pms.createActivityForProject(project, "Implementation", YearWeek.fromDate(week37), YearWeek.fromDate(week42), 42);
	}
}
