package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestRegisterWorkHours {

    PManagementSystem pms;
    DatabaseManager db;
    Project project;
    ProjectActivity activity;
    LocalDate pastDate;
    LocalDate futureDate;

    //TODO: Add test for LocalDate after this period.
    YearWeek week21 = new YearWeek(2016, 21);
    YearWeek week22 = new YearWeek(2016, 22);

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() throws WrongDateException, NoAccessException,
            AlreadyAssignedProjectManagerException, IOException {
        pms = new PManagementSystem();
        db = new DatabaseManager("Employees.txt");

        //Set current date to the may 9th 2016.
        DateServer mockDateServer = mock(DateServer.class);
        pms.setDateServer(mockDateServer);

        LocalDate date = LocalDate.parse("2016-05-09");
        when(pms.getDate()).thenReturn(date);

        pastDate = LocalDate.parse("2016-05-08"); // New date in the past
        futureDate = LocalDate.parse("2016-05-10"); // New date in the future
        assertThat(futureDate.isAfter(pms.getDate()), is(true)); // Test that date is in the future
        assertThat(pastDate.isBefore(pms.getDate()), is(true)); // Test that date is in the past

        //Login a user
        pms.signIn(db.getEmployees().get(0).getId());
        assertTrue(pms.userLoggedIn());

        //Create a project
        LocalDate startDate = LocalDate.parse("2016-05-10");
        LocalDate endDate = LocalDate.parse("2016-06-10");
        project = pms.createProject(startDate, endDate);

        assertThat(pms.getProjects().size(), is(1));

        pms.assignManagerToProject(project);

        assertThat(project.getProjectManager().matches(db.getEmployees().get(0)), is(true));
    }

    //Date in the past, employee assigned to activity, hours less than 24
    @Test
    public void testA() throws Exception {

        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42); // New projectActivity
        assertThat(activity, is(not(nullValue()))); // Test that activity is created

        pms.addEmployeeToActivity(project,activity,pms.getCurrentEmployee());
        //project.addEmployeeToActivity(activity, pms.getCurrentEmployee()); // Add employee to an activity
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee())); // Test that employee is assigned to activity

        pms.registerWorkHours(activity, 120, pastDate);
        assertThat(pms.getCurrentEmployee().getRegisteredWork().isEmpty(), is(false));
        assertThat(pms.getCurrentEmployee().getRegisteredWork().get(0).getDay(), is(pastDate));
        assertThat(pms.getCurrentEmployee().getRegisteredWork().get(0).getMinutes(), is(120));
        assertThat(activity.getRegisteredWork().isEmpty(), is(false));
        assertThat(activity.getRegisteredWork().get(0), is(equalTo(pms.getCurrentEmployee().getRegisteredWork().get(0))));
    }


   // Date in the future, employee assigned to activity, hours less than 24
    @Test
    public void testB() throws Exception {
        expectedEx.expect(WrongDateException.class);
        expectedEx.expectMessage("You can not register work hours in the future.");

        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42); // New projectActivity
        assertThat(activity, is(not(nullValue()))); // Test that activity is created

        pms.addEmployeeToActivity(project,activity,pms.getCurrentEmployee());
        //project.addEmployeeToActivity(activity, pms.getCurrentEmployee()); // Add employee to an activity
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee())); // Test that employee is assigned to activity

        pms.registerWorkHours(activity, 120, futureDate);
    }

   // Date in the past, employee not assigned to activity, hours less than 24
    @Test
    public void testC() throws Exception {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user not assigned to activity.");

        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42); // New projectActivity
        assertThat(activity, is(not(nullValue()))); // Test that activity is created

        assertThat(activity.getEmployees(), not(hasItem(pms.getCurrentEmployee()))); // Test that employee is not assigned to activity

        pms.registerWorkHours(activity, 120, pastDate);
    }

    // Date in the past, employee assigned to activity, hours exceeds 24
    @Test
    public void testD() throws Exception {
        expectedEx.expect(TooManyHoursException.class);
        expectedEx.expectMessage("You can not work more than 24 hours in one day.");

        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42); // New projectActivity
        assertThat(activity, is(not(nullValue()))); // Test that activity is created

        pms.addEmployeeToActivity(project,activity,pms.getCurrentEmployee()); // Add employee to an activity
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee())); // Test that employee is assigned to activity

        pms.registerWorkHours(activity, 25*60, pastDate);
    }

    //Date in the past, employee assigned to activity, hours less than 24
    //same activity new hours exceeds 24
    @Test
    public void testE() throws Exception {
        expectedEx.expect(TooManyHoursException.class);
        expectedEx.expectMessage("You can not work more than 24 hours in one day.");

        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42); // New projectActivity
        assertThat(activity, is(not(nullValue()))); // Test that activity is created

        pms.addEmployeeToActivity(project,activity,pms.getCurrentEmployee()); // Add employee to an activity
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee())); // Test that employee is assigned to activity

        pms.registerWorkHours(activity, 12*60, pastDate);
        pms.registerWorkHours(activity, 25*60, pastDate);
    }

    // Date in the past, employee assigned, hours less than 24
    // other activity exceeds 24 - entered hours
    @Test
    public void testF() throws Exception {
        expectedEx.expect(TooManyHoursException.class);
        expectedEx.expectMessage("You can not work more than 24 hours in one day.");

        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42); // New projectActivity
        ProjectActivity secondActivity = pms.createActivityForProject(project, "ScrumMeeting", week21, week22, 42); // New projectActivity
        assertThat(activity, is(not(nullValue()))); // Test that activity is created
        assertThat(secondActivity, is(not(nullValue()))); // Test that activity is created

        pms.addEmployeeToActivity(project,activity,pms.getCurrentEmployee()); // Add employee to an activity
        pms.addEmployeeToActivity(project,secondActivity,pms.getCurrentEmployee()); // Add employee to an activity
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee())); // Test that employee is assigned to activity

        pms.registerWorkHours(activity, 12*60, pastDate);
        pms.registerWorkHours(secondActivity, 13*60, pastDate);
    }

    //Date in the past, employee assigned to activity, hours less than 0
    @Test
    public void testG() throws Exception {
        expectedEx.expect(NegativeHoursException.class);
        expectedEx.expectMessage("You can not work negative hours.");

        activity = pms.createActivityForProject(project, "Implementation", week21, week22, 42); // New projectActivity
        assertThat(activity, is(not(nullValue()))); // Test that activity is created

        pms.addEmployeeToActivity(project,activity,pms.getCurrentEmployee()); // Add employee to an activity
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee())); // Test that employee is assigned to activity

        pms.registerWorkHours(activity, 12*60, pastDate);
        assertThat(pms.getCurrentEmployee().getRegisteredWork().isEmpty(), is(false));
        assertThat(pms.getCurrentEmployee().getRegisteredWork().get(0).getDay(), is(pastDate));
        assertThat(pms.getCurrentEmployee().getRegisteredWork().get(0).getMinutes(), is(12*60));
        assertThat(activity.getRegisteredWork().isEmpty(), is(false));
        assertThat(activity.getRegisteredWork().get(0), is(equalTo(pms.getCurrentEmployee().getRegisteredWork().get(0))));

        pms.registerWorkHours(activity, (-6)*60, pastDate);

    }

}

