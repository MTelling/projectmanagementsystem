package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestSignIn {

    PManagementSystem pms;
    DatabaseManager db;

    @Before
    public void setup() {
        pms = new PManagementSystem();
        db = new DatabaseManager();
    }

    @Test
    public void signInSuccess() {
        String username = db.getEmployees()[0];
        
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
        assertThat(pms.signIn(username), is(true));
        assertThat(pms.getCurrentEmployee().getName(), is(username));
    }

    @Test
    public void signInNoSuchUser() {
        String username = "john";
        
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
        assertThat(pms.signIn(username), is(false));
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
    }
}
