package dk.dtu.software.group8;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TestYearWeek {

    @Test
    public void testIsAfterWeek() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2016, 42);

        assertThat(second.isAfter(first), is(true));
    }

    @Test
    public void testIsAfterYear() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2017, 37);

        assertThat(second.isAfter(first), is(true));
    }

    @Test
    public void testIsAfterCombi() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2017, 42);

        assertThat(second.isAfter(first), is(true));
    }

    @Test
    public void testIsAfterEarlierWeekLaterYear() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2017, 3);

        assertThat(second.isAfter(first), is(true));
    }

    @Test
    public void testIsAfterFail() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2015, 3);

        assertThat(second.isAfter(first), is(false));
    }

    @Test
    public void testIsBefore() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2015, 3);

        assertThat(second.isBefore(first), is(true));
    }

    @Test
    public void testIsEqualSuccess() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2016, 37);

        assertThat(first.isEqual(second), is(true));
    }

    @Test
    public void testIsEqualFailure() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2016, 42);

        assertThat(first.isEqual(second), is(false));
    }

}
