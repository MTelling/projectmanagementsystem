package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import jdk.internal.dynalink.linker.LinkerServices;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ProjectActivity extends  Activity {

    private int approximatedHours;

    //TODO: This should not be hardcoded here.
    private static final Set<String> LEGAL_TYPES = new HashSet<String>(Arrays.asList("Implementation"));

    //TODO: What if endWeek is week 2, year 2017 and startWeek is week 47, year 2016. How do we check this?
    public ProjectActivity(String activityType,int startWeek,int endWeek,int approximatedHours) throws IncorrectAttributeException {
        if(!LEGAL_TYPES.contains(activityType)) {
            throw new IncorrectAttributeException("The supplied activity type is not a correct activity type.");
        } else if (endWeek - startWeek < 0 ||
                Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date())) > startWeek) {
            String message = "The supplied time period is not a legal time period (Start before now or end before start).";
            throw new IncorrectAttributeException(message);
        } else if (approximatedHours < 1) {
            throw new IncorrectAttributeException("The supplied approximated time is not allowed to be negative.");
        }

        this.activityType = activityType;

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.WEEK_OF_YEAR, startWeek);
        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.WEEK_OF_YEAR, endWeek);

        this.startTime = startTime;
        this.endTime = endTime;
        this.approximatedHours = approximatedHours;
    }

    public int getApproximatedHours() {
        return this.approximatedHours;
    }
}
