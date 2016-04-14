package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestEndActivity extends TestManageProject {

    @Test
    public void testEndProject() {
        project.end();

        assertThat(project.getEndDate(), is(pms.getDate()));
    }

    //TODO: Project does not know current user. Project.end() can therefore be called by anyone.
//    @Test
//    public void testEndProjectNotManager() {
//        expectedEx.expect(NoAccessException.class);
//        expectedEx.expectMessage("Current user is not Project Manager for this project.");
//
//        //Sign in as employee who is not PM.
//        pms.signIn(db.getEmployees()[2]);
//
//        //Test Again. Now without project manager signed in.
//        testEndProject();
//    }
//


}
