package dk.dtu.software.group8;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.naming.InvalidNameException;

import org.junit.Test;

import dk.dtu.software.group8.Exceptions.NoAccessException;

public class TestChangeProjectName extends TestManageProject{

    @Test //Test for string
    public void testChangeNameA() throws InvalidNameException, NoAccessException {
        String name = "Anders";

        pms.changeNameOfProject(project, name);
        assertThat(project.getName(), is(name));
    }

    @Test //Test for Integer
    public void testChangeNameB() throws InvalidNameException, NoAccessException {
        String name = "42";

        pms.changeNameOfProject(project, name);
        assertThat(project.getName(), is(name));
    }

    @Test //Test for float/special char.
    public void testChangeNameC() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Special characters are not allowed in name.");

        String name = "101.2";

        pms.changeNameOfProject(project, name);
    }

    @Test //Test for special char.
    public void testChangeNameD() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Special characters are not allowed in name.");

        String name = "Anders' ide";

        pms.changeNameOfProject(project, name);
    }

    @Test //Test for null
    public void testChangeNameE() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Name cannot be null.");

        String name = null;

        pms.changeNameOfProject(project, name);
    }

    //TODO: The project itself does not know other projects names. We must do this through PManagementSystem
//    @Test //Test for duplicate name
//    public void testChangeNameF() {
//        expectedEx.except(InvalidNameException.class);
//        expectedEx.expectMessage("Name is already assigned to another project.");
//
//        String name = "Test";
//        pms.changeNameOfProject(project, name);
//        assertEquals(project.getId(), name);
//
//        //Create a second project.
//        Calendar startDate = new GregorianCalendar(2016, Calendar.MAY, 10);
//        Calendar endDate = new GregorianCalendar(2016, Calendar.JUNE, 10);
//        Project secondProject = pms.createProject(startDate, endDate);
//        pms.assignManagerToProject(secondProject, emp);
//
//        pms.changeNameOfProject(project, name);
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
//        assertEquals(pms.getCurrentEmployee().getId(), db.getEmployees()[1]);
//
//        //Try to change project name.
//        pms.changeNameOfProject(project, "Test");
//    }
//
    @Test //Test for too long name
    public void testChangeNameH() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Name can only be 30 characters long.");

        String name = "MortenTellingTobiasLindstrømLouiseJustesenMarcusPagh ";

        pms.changeNameOfProject(project, name);
    }
}
