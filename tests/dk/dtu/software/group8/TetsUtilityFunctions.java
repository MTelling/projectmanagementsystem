package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TetsUtilityFunctions extends TestManageProject {

    @Before
    public void setupProjectDates() throws NoAccessException, WrongDateException {
        //Set the project duration differently so we can do cleaner tests:
        pms.manageProjectDates(project, new YearWeek(2016, 19).toLocalDate(), new YearWeek(2016,40).toLocalDate());

    }

    @Test
    public void testGetWorkedMinutes() throws NoAccessException, IncorrectAttributeException, TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed, TooManyHoursException, NegativeHoursException, WrongDateException {
        LocalDate date = LocalDate.parse("2016-05-09");
        ProjectActivity testActivityHours = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 25), 42);

        pms.addEmployeeToActivity(testActivityHours, emp);

        emp.registerWorkHours(testActivityHours,60,date);
        for(int i = 1; i <= 7; i++) {
            emp.registerWorkHours(testActivityHours,i*30,date.minusDays(i));
        }

        assertThat(emp.getTotalRegisteredMinutesOnActivity(testActivityHours),is(equalTo(900)));
        assertThat(emp.getTotalRegisteredMinutesOnDayAndActivityPastWeek(date.minusDays(1), testActivityHours), is(equalTo(840)));
        assertThat(emp.getTotalRegisteredMinutesOnDayAndActivityPastWeek(date, testActivityHours), is(equalTo(690)));

        assertThat(testActivityHours.getTotalRegisteredMinutes(),is(equalTo(900)));
        assertThat(testActivityHours.getTotalRegisteredMinutesOnDay(date.minusDays(7)), is(equalTo(210)));
        assertThat(testActivityHours.getTotalRegisteredMinutesPastWeek(date), is(equalTo(690)));

    }

    @Test
    public void testGetEmployeeActivitiesOnDateDateIsStartDateForActivities() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-09");

        //Create activities
        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 23), 42);
        ProjectActivity activityNotOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 24), new YearWeek(2016, 25), 42);

        pms.addEmployeeToActivity(activityOnDateOne, emp);
        pms.addEmployeeToActivity(activityOnDateTwo, emp);
        pms.addEmployeeToActivity(activityNotOnDateOne, emp);

        List<Activity> activitiesOnDate = pms.getEmployeeActivitiesOnDate(emp, date);
        assertThat(activitiesOnDate, hasItems(activityOnDateOne, activityOnDateTwo));
    }

    @Test
    public void testGetEmployeeActivitiesOnDateDateIsEndDateForActivities() throws Exception {

        //This is the last day in week 25.
        LocalDate date = LocalDate.parse("2016-06-26");

        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation",
                new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityNotOnDateOne = pms.createActivityForProject(project, "Implementation",
                new YearWeek(2016, 19), new YearWeek(2016, 23), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation",
                new YearWeek(2016, 24), new YearWeek(2016, 25), 42);

        pms.addEmployeeToActivity(activityOnDateOne, emp);
        pms.addEmployeeToActivity(activityOnDateTwo, emp);
        pms.addEmployeeToActivity(activityNotOnDateOne, emp);

        List<Activity> activitiesOnDate = pms.getEmployeeActivitiesOnDate(emp, date);
        assertThat(activitiesOnDate, hasItems(activityOnDateOne, activityOnDateTwo));
    }

    @Test
    public void testGetEmployeeActivitiesOnDateEmpty() {
        LocalDate date = LocalDate.parse("2016-05-11");

        List<Activity> activitiesOnDate = pms.getEmployeeActivitiesOnDate(emp, date);
        assertThat(activitiesOnDate.isEmpty(), is(true));
    }

    @Test
    public void testGetRegisteredWorkOnDate() throws Exception {
        LocalDate date = LocalDate.parse("2016-05-09");

        ProjectActivity activityOnDateOne = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 25), 42);
        ProjectActivity activityOnDateTwo = pms.createActivityForProject(project, "Implementation", new YearWeek(2016, 19), new YearWeek(2016, 23), 42);

        pms.addEmployeeToActivity(activityOnDateOne, emp);
        pms.addEmployeeToActivity(activityOnDateTwo, emp);

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

        pms.addEmployeeToActivity(activityOnDateOne, emp);
        pms.addEmployeeToActivity(activityOnDateTwo, emp);

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

        pms.addEmployeeToActivity(activityOnDateOne, emp);
        pms.addEmployeeToActivity(activityOnDateTwo, emp);

        pms.registerWorkHours(activityOnDateOne, 30, date);
        pms.registerWorkHours(activityOnDateOne, 100, date.minusDays(1));
        pms.registerWorkHours(activityOnDateTwo, 300, date.minusDays(2));

        int actualRegisteredMinutes = 30;
        int totalRegisteredMinutes = emp.getTotalRegisteredMinutesOnDayAndActivity(date, activityOnDateOne);

        assertThat(totalRegisteredMinutes, is(equalTo(actualRegisteredMinutes)));
    }
}
