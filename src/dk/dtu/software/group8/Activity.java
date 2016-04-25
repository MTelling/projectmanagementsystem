package dk.dtu.software.group8;

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

    public String getActivityType() {
       return this.activityType;
    }

    public YearWeek getStartWeek() {
        return startTime;
    }

    public YearWeek getEndWeek() {
        return endTime;
    }

}