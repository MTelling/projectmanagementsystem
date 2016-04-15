package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dk.dtu.software.group8.Exceptions.NoAccessException;

public class TestEndActivity extends TestManageProject {

    @Test
    public void testEndProject() throws NoAccessException {
        pms.endProject(project);
        assertThat(project.getEndDate(), is(pms.getDate()));
    }

    
    @Test
    public void testEndProjectNotManager() throws NoAccessException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        String empId = db.getEmployees().get(2).getId();
        pms.signIn(empId);
        
        //Test Again. Now without project manager signed in.
        testEndProject();
    }

}
