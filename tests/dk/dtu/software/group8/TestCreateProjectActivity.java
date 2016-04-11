package dk.dtu.software.group8;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class TestCreateProjectActivity extends TestManageProject {
	
	@Test
	public void testCreateNewProjectActivitySuccess() {
		String activityType = "Implementation";
		int startWeek = 37;
		int endWeek = 42;
		int approximatedHours = 42;
		
		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
		
		assertThat(projectActivity, instanceOf(Activity.class));
		assertThat(projectActivity, instanceOf(ProjectActivity.class));
		
		assertThat(projectActivity.getActivityType(), is(activityType));
		assertThat(projectActivity.getStartWeek(), is(startWeek));
		assertThat(projectActivity.getEndWeek(), is(endWeek));
		assertThat(projectActivity.getApproximatedHours(), is(approximatedHours));
	}
	
	@Test
	public void testCreateNewProjectActivityIncorrectActivityType() {
		String activityType = "1234";
		int startWeek = 37;
		int endWeek = 42;
		int approximatedHours = 42;
		
		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
		
		assertThat(projectActivity, instanceOf(Activity.class));
		assertThat(projectActivity, instanceOf(ProjectActivity.class));
	}
	
	@Test
	public void testCreateNewProjectActivityIncorrectTimePeriod() {
		String activityType = "Implementation";
		int startWeek = 37;
		int endWeek = 36;
		int approximatedHours = 42;
		
		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
		
		assertThat(projectActivity, instanceOf(Activity.class));
		assertThat(projectActivity, instanceOf(ProjectActivity.class));
	}
	
	@Test
	public void testCreateNewProjectActivityIncorrectAproximatedTime() {
		String activityType = "Implementation";
		int startWeek = 37;
		int endWeek = 42;
		int approximatedHours = -42;
		
		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
		
		assertThat(projectActivity, instanceOf(Activity.class));
		assertThat(projectActivity, instanceOf(ProjectActivity.class));
	}

	@Test
	public void testCreateProjectActivity() {
		assertThat(project.getProjectManager(), is(pms.getCurrentEmployee()));
		assertThat(project.getActivities().isEmpty(), is(true));
		
		Activity projectActivity = project.createActivity("Implementation", 37, 42, 42);
		
		assertThat(projectActivity, instanceOf(ProjectActivity.class));
		assertThat(project.getActivities().size()), is(1));
	}
	
	@Test
	public void testCreateProjectActivityNotManager() {
		
	}
}
