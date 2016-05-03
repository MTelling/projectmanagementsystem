package dk.dtu.software.group8;

import java.time.LocalDate;

public abstract class Activity {

    protected String activityType;
    protected LocalDate startTime, endTime;

    public boolean isTimePeriodInActivityDuration(LocalDate startDate, LocalDate endDate) {
        return (startDate.isAfter(startTime) && startDate.isBefore(endTime))
                || (endDate.isAfter(startTime) && endDate.isBefore(endTime));
    }

    public String getActivityType() {
        return this.activityType;
    }

    public void setStartDate(LocalDate date) { this.startTime = date; }

    public void setEndDate(LocalDate date) { this.endTime = date; }

    public LocalDate getStartDate() { return this.startTime; }

    public LocalDate getEndDate() { return this.endTime; }

    public String toString() {
        return activityType + " (" + getStartDate() + " - " + getEndDate() + ")";
    }

}