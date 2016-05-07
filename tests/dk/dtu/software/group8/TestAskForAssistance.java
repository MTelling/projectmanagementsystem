package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestAskForAssistance extends TestManageProject {

    ProjectActivity activity;
    YearWeek week37 = new YearWeek(2016, 37);
    YearWeek week42 = new YearWeek(2016, 42);

    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException,
            TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed, WrongDateException {
        activity = pms.createActivityForProject(project, "Implementation", week37, week42, 42);
        assertThat(activity, is(not(nullValue())));

        pms.addEmployeeToActivity(project, activity, pms.getCurrentEmployee());
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
    }

    @Test // Add employee as consultant
    public void testA() throws NoAccessException, InvalidEmployeeException, EmployeeAlreadyAddedException {
        pms.addEmployeeToActivityAsConsultant(activity, pms.getEmployees().get(1));
        assertThat(activity.getConsultants(), hasItem(pms.getEmployees().get(1)));
    }

    @Test // Current user not assigned to activity
    public void testB() throws NoAccessException, InvalidEmployeeException, EmployeeAlreadyAddedException {
        expectedEx.expect(NoAccessException.class);
        expectedEx.expectMessage("Current user is not assigned to this activity.");

        pms.signIn(pms.getEmployees().get(1).getId());
        assertThat(pms.getCurrentEmployee().getId(), is(pms.getEmployees().get(1).getId()));

        pms.addEmployeeToActivityAsConsultant(activity, pms.getEmployees().get(2));
    }

    @Test // Employee already assigned to activity
    public void testC() throws NoAccessException, TooManyActivitiesException,
            InvalidEmployeeException, EmployeeAlreadyAddedException, NullNotAllowed {
        expectedEx.expect(InvalidEmployeeException.class);
        expectedEx.expectMessage("Employee already assigned to activity.");

        pms.addEmployeeToActivity(project, activity, pms.getEmployees().get(1));
        assertThat(activity.getEmployees(), hasItem(pms.getEmployees().get(1)));

        pms.addEmployeeToActivityAsConsultant(activity, pms.getEmployees().get(1));
    }


    @Test
    public void testD() throws Exception {
        expectedEx.expect(EmployeeAlreadyAddedException.class);
        expectedEx.expectMessage("Employee is already added as consultant.");

        testA();
        testA();
    }
}