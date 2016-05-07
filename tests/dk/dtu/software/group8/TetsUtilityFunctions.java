package dk.dtu.software.group8;

import org.junit.Test;

import java.rmi.server.ExportException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TetsUtilityFunctions extends TestManageProject {

    @Test
    public void testGetEmployeeActivitiesOnDateDateIsStartDateForActivities() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-09");
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
    public void testGetEmployeeActivitiesOnDateDateIsEndDateForActivities() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-26");
        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation",
                new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityNotOnDateOne = pms.createActivityForProject(project, "Implementation",
                new YearWeek(2016, 19), new YearWeek(2016, 23), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation",
                new YearWeek(2016, 24), new YearWeek(2016, 25), 42);

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



    //TODO: Maybe test the functions that get registered work on given date and time here?
    @Test
    public void testGetRegisteredWorkOnDate() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-09");

        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 23), 42);

        pms.addEmployeeToActivity(project, activityOnDateOne, emp);
        pms.addEmployeeToActivity(project, activityOnDateTwo, emp);

        pms.registerWorkHours(activityOnDateOne, 30, date);
        pms.registerWorkHours(activityOnDateTwo, 100, date.minusDays(1));
        pms.registerWorkHours(activityOnDateTwo, 300, date.minusDays(2));

        List<RegisteredWork> actualRegisteredWork = new ArrayList<>();
        actualRegisteredWork.add(emp.getRegisteredWork().get(0));
        List<RegisteredWork> registeredWorkOnDate = emp.getRegisteredWorkOnDate(date);

        assertThat(registeredWorkOnDate, is(equalTo(actualRegisteredWork)));
    }

    @Test
    public void testGetTotalRegisteredWorkOnDay() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-09");

        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 23), 42);

        pms.addEmployeeToActivity(project, activityOnDateOne, emp);
        pms.addEmployeeToActivity(project, activityOnDateTwo, emp);

        pms.registerWorkHours(activityOnDateOne, 30, date);
        pms.registerWorkHours(activityOnDateTwo, 100, date.minusDays(1));
        pms.registerWorkHours(activityOnDateTwo, 300, date.minusDays(2));

        int actualRegisteredMinutes = 30;
        int totalRegisteredMinutes = emp.getTotalRegisteredMinutesOnDay(date);

        assertThat(totalRegisteredMinutes, is(equalTo(actualRegisteredMinutes)));
    }

    @Test
    public void testGetTotalRegisteredWorkOnDayAndActivity() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-09");

        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 23), 42);

        pms.addEmployeeToActivity(project, activityOnDateOne, emp);
        pms.addEmployeeToActivity(project, activityOnDateTwo, emp);

        pms.registerWorkHours(activityOnDateOne, 30, date);
        pms.registerWorkHours(activityOnDateOne, 100, date.minusDays(1));
        pms.registerWorkHours(activityOnDateTwo, 300, date.minusDays(2));

        int actualRegisteredMinutes = 30;
        int totalRegisteredMinutes = emp.getTotalRegisteredMinutesOnDayAndActivity(date, activityOnDateOne);

        assertThat(totalRegisteredMinutes, is(equalTo(actualRegisteredMinutes)));
    }
}
