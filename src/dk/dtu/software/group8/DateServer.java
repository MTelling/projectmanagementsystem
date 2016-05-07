package dk.dtu.software.group8;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Marcus
 */
public class DateServer {
    public LocalDate getDate() {
        return LocalDate.now();
    }
}