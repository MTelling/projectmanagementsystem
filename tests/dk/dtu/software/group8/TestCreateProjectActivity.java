package dk.dtu.software.group8;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class TestCreateProjectActivity extends TestManageProject {
	
//	@Test
//	public void testCreateNewProjectActivitySuccess() {
//		String activityType = "Implementation";
//		int startWeek = 37;
//		int endWeek = 42;
//		int approximatedHours = 42;
//		
//		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
//		
//		assertThat(projectActivity, instanceOf(Activity.class));
//		assertThat(projectActivity, instanceOf(ProjectActivity.class));
//		
//		assertThat(projectActivity.getActivityType(), is(activityType));
//		assertThat(projectActivity.getStartWeek(), is(startWeek));
//		assertThat(projectActivity.getEndWeek(), is(endWeek));
//		assertThat(projectActivity.getApproximatedHours(), is(approximatedHours));
//	}
//	
//	@Test
//	public void testCreateNewProjectActivityIncorrectActivityType() throws IncorrectAttributeException {
//		expectedEx.expect(IncorrectAttributeException.class);
//		expectedEx.expectMessage("The supplied activity type is not a correct activity type.");
//		
//		String activityType = "1234";
//		int startWeek = 37;
//		int endWeek = 42;
//		int approximatedHours = 42;
//		
//		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
//	}
//	
//	@Test
//	public void testCreateNewProjectActivityIncorrectTimePeriod() throws IncorrectAttributeException {
//		expectedEx.expect(IncorrectAttributeException.class);
//		expectedEx.expectMessage("The supplied time period is not a legal time period (Start before now or end before start).");
//		
//		String activityType = "Implementation";
//		int startWeek = 37;
//		int endWeek = 36;
//		int approximatedHours = 42;
//		
//		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
//	}
//	
//	@Test
//	public void testCreateNewProjectActivityIncorrectAproximatedTime() throws IncorrectAttributeException {
//		expectedEx.expect(IncorrectAttributeException.class);
//		expectedEx.expectMessage("The supplied approximated time is not allowed to be negative.");
//		
//		String activityType = "Implementation";
//		int startWeek = 37;
//		int endWeek = 42;
//		int approximatedHours = -42;
//		
//		Activity projectActivity = new ProjectActivity(activityType, startWeek, endWeek, approximatedHours);
//	}
//
//	@Test
//	public void testCreateProjectActivity() {
//		assertThat(project.getProjectManager(), is(pms.getCurrentEmployee()));
//		assertThat(project.getActivities().isEmpty(), is(true));
//		
//		Activity projectActivity = project.createActivity("Implementation", 37, 42, 42);
//		
//		assertThat(projectActivity, instanceOf(ProjectActivity.class));
//		assertThat(project.getActivities().size()), is(1));
//	}
//	
//	@Test
//	public void testCreateProjectActivityNotManager() throws IncorrectAttributeException {
//		expectedEx.expect(NoAccessException.class);
//		expectedEx.expectMessage("Current user is not Project Manager for this project.");
//
//		//Sign in as employee who is not PM.
//		pms.signIn(db.getEmployees()[2]);
//		
//		project.createActivity("Implementation", 37, 42, 42);
//	}
}
