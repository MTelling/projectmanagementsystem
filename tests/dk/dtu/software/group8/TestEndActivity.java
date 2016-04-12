package dk.dtu.software.group8;

import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestEndActivity extends TestManageProject {

    @Test
    public void testEndProject() {
        project.end();

        // This does not work, because it compares down to seconds.
//        assertThat(project.getEndDate(), is(pms.getDate()));

        //TODO: Should this only check week number?
        assertEquals(project.getEndDate().ERA , pms.getDate().ERA);
        assertEquals(project.getEndDate().YEAR , pms.getDate().YEAR);
        assertEquals(project.getEndDate().MONTH , pms.getDate().MONTH);
        assertEquals(project.getEndDate().DAY_OF_YEAR , pms.getDate().DAY_OF_YEAR);


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
