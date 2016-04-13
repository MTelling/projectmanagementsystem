package dk.dtu.software.group8;

import java.time.Period;
import java.util.Calendar;

public abstract class Activity {

    protected String activityType;
    protected Calendar startTime, endTime;

    //TODO: In the class diagram, this method returns a boolean. Should it?
    public void end() {
        this.endTime = Calendar.getInstance();
    }

    public boolean isTimePeriodInActivityDuration(Calendar date) {
        return date.after(startTime) && date.before(endTime);
    }


    public String getActivityType() {
       return this.activityType;
    }

    public int getStartWeek() {
        return startTime.get(Calendar.WEEK_OF_YEAR);
    }

    public int getEndWeek() {
        return endTime.get(Calendar.WEEK_OF_YEAR);
    }
}