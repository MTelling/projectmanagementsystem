package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import org.junit.Test;

import javax.naming.InvalidNameException;
import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestChangeProjectName extends TestManageProject{

    /**
     * Created by Tobias
     */
    @Test //Test for string
    public void testChangeNameA() throws InvalidNameException, NoAccessException {
        String name = "Anders";

        pms.changeNameOfProject(project, name);
        assertThat(project.getName(), is(name));
    }

    /**
     * Created by Marcus
     */
    @Test //Test for Integer
    public void testChangeNameB() throws InvalidNameException, NoAccessException {
        String name = "42";

        pms.changeNameOfProject(project, name);
        assertThat(project.getName(), is(name));
    }

    /**
     * Created by Morten
     */
    @Test //Test for float/special char.
    public void testChangeNameC() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Special characters are not allowed in name.");

        String name = "101.2";

        pms.changeNameOfProject(project, name);
    }

    /**
     * Created by Tobias
     */
    @Test //Test for special char.
    public void testChangeNameD() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Special characters are not allowed in name.");

        String name = "Anders' ide";

        pms.changeNameOfProject(project, name);
    }

    /**
     * Created by Marcus
     */
    @Test //Test for null
    public void testChangeNameE() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Name cannot be null.");

        String name = null;

        pms.changeNameOfProject(project, name);
    }

    /**
     * Created by Morten
     */
    @Test //Test for duplicate name
    public void testChangeNameF() throws NoAccessException, InvalidNameException, WrongDateException, AlreadyAssignedProjectManagerException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Name is already assigned to another project.");

        String name = "Test";
        pms.changeNameOfProject(project, name);
        assertEquals(project.getName(), name);

        //Create a second project.
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");
        Project secondProject = pms.createProject(startDate, endDate);
        pms.assignManagerToProject(secondProject);

        pms.changeNameOfProject(project, name);
    }

    /**
     * Created by Tobias
     */
    @Test //User is not project manager.
    public void testChangeNameG() throws NoAccessException, InvalidNameException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not Project Manager for this project.");

        //Set the current employee to one who is not
        pms.signIn(pms.getEmployees().get(1).getId());
        assertEquals(pms.getCurrentEmployee().getId(), pms.getEmployees().get(1).getId());

        //Try to change project name.
        pms.changeNameOfProject(project, "Test");
    }

    /**
     * Created by Marcus
     */
    @Test //Test for too long a name
    public void testChangeNameH() throws InvalidNameException, NoAccessException {
        expectedEx.expect(InvalidNameException.class);
        expectedEx.expectMessage("Name can only be 30 characters long.");

        String name = "MortenTellingTobiasLindstrømLouiseJustesenMarcusPagh ";

        pms.changeNameOfProject(project, name);
    }
}