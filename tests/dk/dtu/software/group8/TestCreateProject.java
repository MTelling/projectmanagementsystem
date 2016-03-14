package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCreateProject {
	PManagmentSystem pms;
	DatabaseManager db;
	
	@Before
	public void setup() {
		pms = new PManagmentSystem();
		db = new DatabaseManager();
	}
	
	@Test
	public void signInSuccess() {		
		String username = db.getEmployees()[0];
		assertEquals(pms.getCurrentEmployee(), null);
		assertTrue(pms.signIn(username));
		assertEquals(pms.getCurrentEmployee().getName(), username);
	}
	
//	@Test
//	public void signInNoSuchUser() {
//		String username = "john";
//		assertEquals(pms.getCurrentEmployee(), null);
//		assertFalse(pms.signIn(username));
//		assertEquals(pms.getCurrentEmployee(), null);
//	}
	
//	@Test
//	public void createProjectSignedIn() {
//		pms.signIn(db.getEmployees()[0]);
//		
//		asserEquals(pms.getProjects().size(), 0);
//		pms.createProject();
//		assertEquals(pms.getProjects().size(), 1);
//	}
	
//	@Test
//	public void createProjectNotSignedIn() {
//		asserEquals(pms.getProjects().size(), 0);
//		pms.createProject();
//		assertEquals(pms.getProjects().size(), 0);
//	}
	
//	@Test
//	public void testForActualProject() {
//		pms.signIn(db.getEmployees()[0]);
//		
//		pms.createProject();
//		assertThat(pms.getProjects()[0], instanceOf(Project.class));
//		assertThat(pms.getProjects()[0].getID(), RegexMatcher.matches("^[0-9]{1,6}$"));
//	}
}
