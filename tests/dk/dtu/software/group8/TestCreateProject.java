package dk.dtu.software.group8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestCreateProject {
	
	@Test
	public void signInSuccess() {
		PManagmentSystem pms = new PManagmentSystem();
		DatabaseManager db = new DatabaseManager();
		
		String username = db.getEmployees()[0];
		assertEquals(pms.getCurrentEmployee(), null);
		assertTrue(pms.signIn(username));
		assertEquals(pms.getCurrentEmployee().getName(), username);
	}
	
//	@Test
//	public void signInNoSuchUser() {
//		PManagmentSystem pms = new PManagmentSystem();
//		
//		String username = "john";
//		assertEquals(pms.getCurrentEmployee(), null);
//		assertFalse(pms.signIn(username));
//		assertEquals(pms.getCurrentEmployee(), null);
//	}
	
//	@Test
//	public void createProjectSignedIn() {
//		PManagmentSystem pms = new PManagmentSystem();
//		DatabaseManager db = new DatabaseManager();
//		
//		String username = db.getEmployees()[0];
//		pms.signIn(username);
//		
//		asserEquals(pms.getProjects().size(), 0);
//		pms.createProject();
//		assertEquals(pms.getProjects().size(), 1);
//	}
	
//	@Test
//	public void createProjectNotSignedIn() {
//		PManagmentSystem pms = new PManagmentSystem();
//		
//		asserEquals(pms.getProjects().size(), 0);
//		pms.createProject();
//		assertEquals(pms.getProjects().size(), 0);
//	}
}
