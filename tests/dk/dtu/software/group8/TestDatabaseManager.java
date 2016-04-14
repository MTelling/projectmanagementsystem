package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


/**
 * Created by Morten on 14/04/16.
 */
public class TestDatabaseManager {

    private DatabaseManager db;

    @Test
    public void TestStandardDB() {
        db = new DatabaseManager();

        assertThat(db, is(not(nullValue())));
        assertThat(db.getEmployees().get(0), instanceOf(Employee.class));
    }

    @Test
    public void TestExampleDB() {
        db = new DatabaseManager("EmployeesTest.txt");

        assertThat(db.getEmployees().get(0).getId(), is("huba"));
        assertThat(db.getEmployees().get(0).getFirstName(), is("Hubert"));
        assertThat(db.getEmployees().get(0).getLastName(), is("Baumeister"));
        assertThat(db.getEmployees().size(), is(5));
    }


    //TODO: should probably test with a file that is nonexistent and get the correct error.
}
