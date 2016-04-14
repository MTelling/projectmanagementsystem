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
        String iD = db.getEmployees().get(0).getId();
        assertEquals(pms.getCurrentEmployee(), null);
        assertTrue(pms.signIn(iD));
        assertEquals(pms.getCurrentEmployee().getId(), iD);
    }

    @Test
    public void signInNoSuchUser() {
        String iD = "john";
        assertEquals(pms.getCurrentEmployee(), null);
        assertFalse(pms.signIn(iD));
        assertEquals(pms.getCurrentEmployee(), null);
    }
}
