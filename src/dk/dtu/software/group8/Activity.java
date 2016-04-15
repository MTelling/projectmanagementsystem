package dk.dtu.software.group8;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public abstract class Activity {

    protected String activityType;
    protected LocalDate startTime, endTime;

    public void end() {
        this.endTime = LocalDate.now();
    }

    public boolean isTimePeriodInActivityDuration(LocalDate date) {
        return date.isAfter(startTime) && date.isBefore(endTime);
    }

    public String getActivityType() {
       return this.activityType;
    }

    public int getStartWeek() {
        return startTime.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }

    public int getEndWeek() {
    	return endTime.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }

}