package dk.dtu.software.group8;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class YearWeek {

    private int week, year;

    public YearWeek(int year, int week) {
        this.year = year;
        this.week = week;
    }

    public boolean isAfter(YearWeek other) {
       if(this.getYear() > other.getYear())
           return true;
        else if(this.getWeek() > other.getWeek() && this.getYear() == other.getYear())
           return true;
        else
           return false;
    }

    public boolean isBefore(YearWeek other) {
        return !isAfter(other);
    }

    public static YearWeek fromDate(LocalDate date) {
        int weekNumber = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        return new YearWeek(date.getYear(), weekNumber);
    }

    public String toString() {
        return this.getYear() + ", " + this.getWeek();
    }

    public int getYear() {
        return this.year;
    }

    public int getWeek() {
        return this.week;
    }
}
