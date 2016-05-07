package dk.dtu.software.group8;

import dk.dtu.software.group8.Exceptions.*;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestExtractWorkReport  extends TestManageProject {

    ProjectActivity activity;
    YearWeek week37 = new YearWeek(2016, 37);
    YearWeek week42 = new YearWeek(2016, 42);


    @Before
    public void setupActivity() throws IncorrectAttributeException, NoAccessException, TooManyActivitiesException, EmployeeAlreadyAddedException, NullNotAllowed, WrongDateException {
        activity = pms.createActivityForProject(project, "Implementation", week37, week42, 42);
        assertThat(activity, is(not(nullValue())));
        pms.addEmployeeToActivity(activity, pms.getCurrentEmployee());
        assertThat(activity.getEmployees(), hasItem(pms.getCurrentEmployee()));
    }

    @Test
    public void extractReport() throws IOException {
        WorkReport DankReport = new WorkReport(pms, project);
        DankReport.make();
        String fileName = "Not available" + pms.getDate();
        assertThat(DankReport.getFileName(),is(equalTo(fileName)));

        byte[] file1Bytes = Files.readAllBytes(Paths.get(DankReport.getFileName() + ".html"));
        byte[] file2Bytes = Files.readAllBytes(Paths.get("TestWorkReport1.html"));

        String file1 = new String(file1Bytes, StandardCharsets.UTF_8);
        String file2 = new String(file2Bytes, StandardCharsets.UTF_8);
        file1 = file1.replaceAll("\\r\\n+|\\r+|\\n+|\\t+", "");
        file2 = file2.replaceAll("\\r\\n+|\\r+|\\n+|\\t+", "");

        assertThat(file1,is(equalTo(file2)));
    }

    @Test
    public void extractReportWithConsultants() throws InvalidEmployeeException, IOException {
        activity.assignConsultantToActivity(pms.getEmployees().get(2));

        WorkReport DankReport = new WorkReport(pms, project);
        DankReport.make();
        String fileName = "Not available" + pms.getDate();
        assertThat(DankReport.getFileName(),is(equalTo(fileName)));

        byte[] file1Bytes = Files.readAllBytes(Paths.get(DankReport.getFileName() + ".html"));
        byte[] file2Bytes = Files.readAllBytes(Paths.get("TestWorkReport2.html"));

        String file1 = new String(file1Bytes, StandardCharsets.UTF_8);
        String file2 = new String(file2Bytes, StandardCharsets.UTF_8);
        file1 = file1.replaceAll("\\r\\n+|\\r+|\\n+|\\t+", "");
        file2 = file2.replaceAll("\\r\\n+|\\r+|\\n+|\\t+", "");

        assertThat(file1,is(equalTo(file2)));
    }

    @Test
    public void extractReportWithProjectName() throws TooManyActivitiesException, EmployeeAlreadyAddedException, InvalidEmployeeException, InvalidNameException, IOException {
        activity.assignConsultantToActivity(pms.getEmployees().get(2));
        activity.addEmployee(pms.getEmployees().get(3));
        project.setName("PisseMegetSur");

        WorkReport DankReport = new WorkReport(pms, project);
        DankReport.make();
        assertThat(DankReport.getFileName(),is(equalTo(project.getName() + pms.getDate())));

        byte[] file1Bytes = Files.readAllBytes(Paths.get(DankReport.getFileName() + ".html"));
        byte[] file2Bytes = Files.readAllBytes(Paths.get("TestWorkReport3.html"));

        String file1 = new String(file1Bytes, StandardCharsets.UTF_8);
        String file2 = new String(file2Bytes, StandardCharsets.UTF_8);
        file1 = file1.replaceAll("\\r\\n+|\\r+|\\n+|\\t+", "");
        file2 = file2.replaceAll("\\r\\n+|\\r+|\\n+|\\t+", "");

        assertThat(file1,is(equalTo(file2)));
    }
}