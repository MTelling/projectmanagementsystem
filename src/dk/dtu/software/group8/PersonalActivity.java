package dk.dtu.software.group8;


import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.WrongDateException;

import java.time.LocalDate;

public class PersonalActivity extends Activity {

    public PersonalActivity(String activityType, LocalDate startDate, LocalDate endDate) throws IncorrectAttributeException, WrongDateException {
        if(!activityType.matches("[a-zA-Z ]{3,}")) {
            throw new IncorrectAttributeException("The supplied activity type is not a correct activity type!");
        } else if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
            String message = "The supplied time period is not a legal time period (Start before now or end before start)!";
            throw new WrongDateException(message);
        }

        this.activityType = activityType;
        this.startTime = startDate;
        this.endTime = endDate;
    }
}
