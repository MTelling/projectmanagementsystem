package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.WrongDateException;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public abstract class Activity {

    protected String activityType;
    protected YearWeek startTime, endTime;

    public void end() {
        this.endTime = YearWeek.fromDate(LocalDate.now());
    }

    public boolean isTimePeriodInActivityDuration(YearWeek date) {
        return date.isAfter(startTime) && date.isBefore(endTime);
    }

    public void setStartWeek(YearWeek week) { this.startTime = week; }

    public void setEndWeek(YearWeek week) { this.endTime = week; }

    public String getActivityType() {
       return this.activityType;
    }

    public YearWeek getStartWeek() {
        return startTime;
    }

    public YearWeek getEndWeek() { return endTime; }

}