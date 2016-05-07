package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Morten
 */
public class TestEndActivity extends TestManageProject {

    /**
     * Created by Morten
     */
    @Test
    public void testEndProject() throws NoAccessException, WrongDateException {
        pms.endProject(project);
        assertThat(project.getEndDate(), is(pms.getDate()));
    }

    /**
     * Created by Tobias
     */
    @Test
    public void testEndProjectNotManager() throws NoAccessException, WrongDateException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Sign in as employee who is not PM.
        String empId = pms.getEmployees().get(2).getId();
        pms.signIn(empId);
        
        //Test Again. Now without project manager signed in.
        testEndProject();
    }
}