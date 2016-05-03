package dk.dtu.software.group8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
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
        else return this.getWeek() > other.getWeek() && this.getYear() == other.getYear();
    }

    public boolean isBefore(YearWeek other) {
        return !isAfter(other);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof YearWeek ) {
            return this.getYear() == ((YearWeek)obj).getYear() && this.getWeek() == ((YearWeek)obj).getWeek();
        }
        return false;
    }


    public LocalDate toLocalDate() {
        return LocalDate.parse("" + this.getYear() + "-W" + this.getWeek() + "-1", DateTimeFormatter.ISO_WEEK_DATE);
    }

    public static YearWeek fromDate(LocalDate date) {
        int weekNumber = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
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
