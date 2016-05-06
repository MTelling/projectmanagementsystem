package dk.dtu.software.group8;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TetsUtilityFunctions extends  TestManageProject {

    @Test
    public void testGetEmployeeActivitiesOnDate() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-11");
        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 23), 42);
        ProjectActivity activityNotOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 24), new YearWeek(2016, 25), 42);

        pms.addEmployeeToActivity(project, activityOnDateOne, emp);
        pms.addEmployeeToActivity(project, activityOnDateTwo, emp);
        pms.addEmployeeToActivity(project, activityNotOnDateOne, emp);

        List<Activity> activitiesOnDate = pms.getEmployeeActivitiesOnDate(emp, date);
        assertThat(activitiesOnDate, hasItems(activityOnDateOne, activityOnDateTwo));
    }

    @Test
    public void testGetEmployeeActivitiesOnDateEmpty() {
        LocalDate date = LocalDate.parse("2016-05-11");

        List<Activity> activitiesOnDate = pms.getEmployeeActivitiesOnDate(emp, date);
        assertThat(activitiesOnDate.isEmpty(), is(true));
    }

}
