package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.NegativeHoursException;
import dk.dtu.software.group8.Exceptions.TooManyHoursException;

import java.time.LocalDate;

public class RegisteredWork {
    private Employee employee;
    private Activity activity;
    private LocalDate day;
    private int minutes;

    /**
     * Created by Marcus
     */
    public RegisteredWork (Employee employee, Activity activity, LocalDate day, int minutes) throws TooManyHoursException, NegativeHoursException {
        this.employee = employee;
        this.activity = activity;
        this.day = day;
        this.minutes = 0;
        this.addWork(minutes);
    }

    /**
     * Created by Morten
     */
    public boolean matches(Employee employee, Activity activity, LocalDate day) {
        return
                this.employee.equals(employee)
                && this.activity.equals(activity)
                && this.day.equals(day);
    }

    /**
     * Created by Tobias
     */
    public void addWork(int minutes) throws TooManyHoursException, NegativeHoursException {
        if(this.minutes + minutes > 1440) { //More than 24 hours in one day
            throw new TooManyHoursException("You can not work more than 24 hours in one day.");
        } else if (this.minutes + minutes < 0) {
            throw new NegativeHoursException("You can not work negative hours.");
        } else {
            this.minutes += minutes;
        }
    }

    /**
     * Created by Marcus
     */
    public LocalDate getDay() {
        return this.day;
    }

    /**
     * Created by Morten
     */
    public int getMinutes() {
        return this.minutes;
    }

    /**
     * Created by Tobias
     */
    public Employee getEmployee() {
        return this.employee;
    }
}