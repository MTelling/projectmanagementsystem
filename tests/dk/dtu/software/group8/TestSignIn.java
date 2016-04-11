package dk.dtu.software.group8;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertEquals(pms.getCurrentEmployee(), null);
        assertTrue(pms.signIn(username));
        assertEquals(pms.getCurrentEmployee().getName(), username);
    }

    @Test
    public void signInNoSuchUser() {
        String username = "john";
        assertEquals(pms.getCurrentEmployee(), null);
        assertFalse(pms.signIn(username));
        assertEquals(pms.getCurrentEmployee(), null);
    }
}
