package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestCreateProjectActivity extends TestManageProject {
	
	@Test
	public void testCreateNewProjectActivitySuccess() throws IncorrectAttributeException {
		String activityType = "Implementation";
		int startWeek = 37;
		int endWeek = 42;
		int approximatedHours = 42;

		ProjectActivity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);

		assertThat(projectActivity, instanceOf(Activity.class));
		assertThat(projectActivity, instanceOf(ProjectActivity.class));

		assertThat(projectActivity.getActivityType(), is(activityType));
		assertThat(projectActivity.getStartWeek(), is(startWeek));
		assertThat(projectActivity.getEndWeek(), is(endWeek));
		assertThat(projectActivity.getApproximatedHours(), is(approximatedHours));
	}

	@Test
	public void testCreateNewProjectActivityIncorrectActivityType() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied activity type is not a correct activity type.");

		String activityType = "1234";
		int startWeek = 37;
		int endWeek = 42;
		int approximatedHours = 42;

		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
		assertThat(projectActivity, is(nullValue()));
	}

	@Test
	public void testCreateNewProjectActivityIncorrectTimePeriod() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied time period is not a legal time period (Start before now or end before start).");

		String activityType = "Implementation";
		int startWeek = 37;
		int endWeek = 36;
		int approximatedHours = 42;

		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
		assertThat(projectActivity, is(nullValue()));
	}

	@Test
	public void testCreateNewProjectActivityIncorrectAproximatedTime() throws IncorrectAttributeException {
		expectedEx.expect(IncorrectAttributeException.class);
		expectedEx.expectMessage("The supplied approximated time is not allowed to be negative.");

		String activityType = "Implementation";
		int startWeek = 37;
		int endWeek = 42;
		int approximatedHours = -42;

		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
		assertThat(projectActivity, is(nullValue()));
	}

	@Test
	public void testCreateProjectActivity() throws IncorrectAttributeException, NoAccessException{
		assertThat(project.getProjectManager(), is(pms.getCurrentEmployee()));
		assertThat(project.getActivities().isEmpty(), is(true));

		int numOfActivities = project.getActivities().size();

		Activity projectActivity = project.createActivity("Implementation", 37, 42, 42);

		assertThat(projectActivity, instanceOf(ProjectActivity.class));
		assertThat(project.getActivities().size(), is(numOfActivities + 1));
	}

	//TODO: Project doesn't have access to current user.
	@Test
	public void testCreateProjectActivityNotManager() throws IncorrectAttributeException, NoAccessException {
		expectedEx.expect(NoAccessException.class);
		expectedEx.expectMessage("Current user is not Project Manager for this project.");

		//Sign in as employee who is not PM.
		pms.signIn(db.getEmployees()[2]);

		project.createActivity("Implementation", 37, 42, 42);
	}
}
