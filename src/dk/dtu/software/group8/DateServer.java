package dk.dtu.software.group8;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Morten on 11/04/16.
 */
public class DateServer {

    private Calendar calendar;

    public DateServer() {
        this.calendar = new GregorianCalendar();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
