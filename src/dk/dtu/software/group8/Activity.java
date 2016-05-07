package dk.dtu.software.group8;

import java.time.LocalDate;

public abstract class Activity {

    protected String activityType;
    protected LocalDate startTime, endTime;

    /**
     * Created by Tobias
     */
    public boolean isTimePeriodInActivityDuration(LocalDate startDate, LocalDate endDate) {
        return (startDate.isAfter(startTime) && startDate.isBefore(endTime))
                || (endDate.isAfter(startTime) && endDate.isBefore(endTime))
                || (endDate.isEqual(endTime))
                || (startDate.isEqual(startTime));
    }

    /**
     * Created by Marcus
     */
    public String getActivityType() {
        return this.activityType;
    }

    /**
     * Created by Morten
     */
    public void setStartDate(LocalDate date) { this.startTime = date; }

    /**
     * Created by Tobias
     */
    public void setEndDate(LocalDate date) { this.endTime = date; }

    /**
     * Created by Marcus
     */
    public LocalDate getStartDate() { return this.startTime; }

    /**
     * Created by Morten
     */
    public LocalDate getEndDate() { return this.endTime; }

    /**
     * Created by Tobias
     */
    public abstract String toString();
}