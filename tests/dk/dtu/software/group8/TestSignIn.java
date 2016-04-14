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
        String iD = db.getEmployees().get(0).getId();
        
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
        assertThat(pms.signIn(iD), is(true));
        assertThat(pms.getCurrentEmployee().getId(), is(iD));
    }

    @Test
    public void signInNoSuchUser() {
        String iD = "john";
        
        assertThat(pms.getCurrentEmployee(), is(nullValue()));
        assertThat(pms.signIn(iD), is(false));
        assertThat(pms.getCurrentEmployee(), is(nullValue()));

    }
}
