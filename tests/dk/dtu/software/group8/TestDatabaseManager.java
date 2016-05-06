package dk.dtu.software.group8;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by Morten on 14/04/16.
 */
public class TestDatabaseManager {


    private DatabaseManager db;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void TestStandardDB() throws IOException {

        db = new DatabaseManager("Employees.txt");

        assertThat(db, is(not(nullValue())));
        assertThat(db.getEmployees().get(0), instanceOf(Employee.class));
    }

    @Test
    public void TestExampleDB() throws IOException{
        db = new DatabaseManager("EmployeesTest.txt");

        assertThat(db.getEmployees().get(0).getId(), is("huba"));
        assertThat(db.getEmployees().get(0).getFirstName(), is("Hubert"));
        assertThat(db.getEmployees().get(0).getLastName(), is("Baumeister"));
        assertThat(db.getEmployees().size(), is(5));
    }


    @Test
    public void testWrongDB() throws IOException{
        expectedEx.expect(IOException.class);

        db = new DatabaseManager("NotThere.txt");
    }

}
