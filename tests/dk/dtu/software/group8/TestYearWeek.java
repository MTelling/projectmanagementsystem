package dk.dtu.software.group8;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.mockito.cglib.core.Local;

import java.time.LocalDate;

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

        assertThat(first.equals(second), is(true));
    }

    @Test
    public void testIsEqualFailure() {
        YearWeek first = new YearWeek(2016, 37);
        YearWeek second = new YearWeek(2016, 42);

        assertThat(first.equals(second), is(false));
    }

    @Test
    public void testIsEqualDifferentObject() {
        YearWeek first = new YearWeek(2016, 37);
        LocalDate second = new YearWeek(2016, 37).toLocalDate();

        assertThat(first.equals(second), is(false));
    }

    @Test
    public void testFromDate() {
        YearWeek week37 = new YearWeek(2016, 37);
        YearWeek fromDate = YearWeek.fromDate(LocalDate.parse("2016-09-12"));
        assertThat(fromDate, is(equalTo(week37)));
    }

    @Test
    public void testToLocalDate() {
        YearWeek week37 = new YearWeek(2016, 37);
        LocalDate localDate = LocalDate.parse("2016-09-12");
        assertThat(week37.toLocalDate(), is(equalTo(localDate)));
    }

}
