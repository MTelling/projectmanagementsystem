package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Tobias
 */
public class TestSignIn {

    PManagementSystem pms;
    DatabaseManager db;

    /**
     * Created by Tobias
     */
    @Before
    public void setup() throws IOException {
        pms = new PManagementSystem();
        db = new DatabaseManager("Employees.txt");
    }

    /**
     * Created by Marcus
     */
    @Test
    public void signInSuccess() {
        String iD = db.getEmployees().get(0).getId();
        
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
        assertThat(pms.signIn(iD), is(true));
        assertThat(pms.getCurrentEmployee().getId(), is(iD));
    }

    /**
     * Created by Morten
     */
    @Test
    public void signInNoSuchUser() {
        String iD = "john";
        
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
        assertThat(pms.signIn(iD), is(false));
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
    }
}