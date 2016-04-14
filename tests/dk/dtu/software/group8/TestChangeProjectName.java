package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.naming.InvalidNameException;

import org.junit.Test;

public class TestChangeProjectName extends TestManageProject{

    @Test //Test for string
    public void testChangeNameA() throws InvalidNameException {
        String name = "Anders";

        project.setName(name);
        assertThat(project.getName(), is(name));
    }

    @Test //Test for Integer
    public void testChangeNameB() throws InvalidNameException {
        String name = "42";

        project.setName(name);
        assertThat(project.getName(), is(name));
    }

    @Test //Test for float/special char.
    public void testChangeNameC() throws InvalidNameException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Special characters are not allowed in name.");

        String name = "101.2";

        project.setName(name);
    }

    @Test //Test for special char.
    public void testChangeNameD() throws InvalidNameException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Special characters are not allowed in name.");

        String name = "Anders' ide";

        project.setName(name);
    }

    @Test //Test for null
    public void testChangeNameE() throws InvalidNameException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Name cannot be null.");

        String name = null;

        project.setName(name);
    }

    //TODO: The project itself does not know other projects names. We must do this through PManagementSystem
//    @Test //Test for duplicate name
//    public void testChangeNameF() {
//        expectedEx.except(InvalidNameException.class);
//        expectedEx.expectMessage("Name is already assigned to another project.");
//
//        String name = "Test";
//        project.setName(name);
//        assertEquals(project.getName(), name);
//
//        //Create a second project.
//        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);
//        Project secondProject = pms.createProject(startDate, endDate);
//        secondProject.setProjectManager(emp);
//
//        secondProject.setName(name);
//    }
//

    //TODO: The project knows who the project manager is, but not who the current user is.
//    @Test //User is not project manager.
//    public void testChangeNameG() {
//        expectedEx.expect(NoAccessException.class);
//        expectedEx.expectMessage("Current user is not Project Manager for this project.");
//
//        //Set the current employee to one who is not
//        pms.signIn(db.getEmployees()[1]);
//        assertEquals(pms.getCurrentEmployee().getName(), db.getEmployees()[1]);
//
//        //Try to change project name.
//        project.setName("Test");
//    }
//
    @Test //Test for too long name
    public void testChangeNameH() throws InvalidNameException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Name can only be 30 characters long.");

        String name = "MortenTellingTobiasLindstrømLouiseJustesenMarcusPagh ";

        project.setName(name);
    }
}
