package dk.dtu.software.group8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;

/**
 * Created by Tobias
 */
public class YearWeek {

    private int week, year;

    /**
     * Created by Tobias
     */
    public YearWeek(int year, int week) {
        this.year = year;
        this.week = week;
    }

    /**
     * Created by Marcus
     */
    public boolean isAfter(YearWeek other) {
       return this.getYear() > other.getYear() || this.getWeek() >= other.getWeek() && this.getYear() == other.getYear();
    }

    /**
     * Created by Morten
     */
    public boolean isBefore(YearWeek other) {
        return !isAfter(other);
    }

    /**
     * Created by Tobias
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof YearWeek ) {
            return this.getYear() == ((YearWeek)obj).getYear() && this.getWeek() == ((YearWeek)obj).getWeek();
        }
        return false;
    }

    /**
     * Created by Marcus
     */
    public LocalDate toLocalDate() {
        return LocalDate.parse("" + this.getYear() + " " + this.getWeek(),
                new DateTimeFormatterBuilder().appendPattern("YYYY w")
                        .parseDefaulting(WeekFields.ISO.dayOfWeek(), 1)
                        .toFormatter());
    }

    /**
     * Created by Morten
     */
    public static YearWeek fromDate(LocalDate date) {
        int weekNumber = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        return new YearWeek(date.plusDays(3).getYear(), weekNumber);
    }

    /**
     * Created by Tobias
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Created by Marcus
     */
    public int getWeek() {
        return this.week;
    }
}