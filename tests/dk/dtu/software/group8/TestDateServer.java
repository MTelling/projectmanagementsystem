package dk.dtu.software.group8;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Marcus
 */
public class TestDateServer {

    @Test
    public void testDateServerGivesToday() {

        DateServer dateServer = new DateServer();

        LocalDate dateServerDate = dateServer.getDate();

        LocalDate today = LocalDate.now();

        assertThat(dateServerDate, is(today));

    }
}