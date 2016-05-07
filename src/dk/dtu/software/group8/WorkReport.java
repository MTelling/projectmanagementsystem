package dk.dtu.software.group8;

import org.mockito.cglib.core.Local;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WorkReport {

    private PManagementSystem pms;
    private Project project;
    private String projectName;
    private String fileName;

    public WorkReport(PManagementSystem pms, Project project) throws IOException {
        this.pms = pms;
        this.project = project;
        if(project.getName() == null) {
            this.projectName = "Not available";
        } else {
            this.projectName = project.getName();
        }
        fileName = projectName + pms.getDate().toString();
    }

    private static String subForData(String str, String[] tags, Map<String, String> data) {
        //Split string by linebreaks
        //TODO: Mac users might have issues with this, as their line terminator might be different
        String strLines[] = str.split("\\r\\n|\\n|\\r");

        //Escape weird characters in tags to use in regex
        String tag1 = tags[0].replaceAll("[-.\\+*?\\[^\\]$(){}=!<>|:\\\\]", "\\\\$0");
        String tag2 = tags[1].replaceAll("[-.\\+*?\\[^\\]$(){}=!<>|:\\\\]", "\\\\$0");

        Pattern betweenTags = Pattern.compile("(" + tag1 + ")(.*?)(" + tag2 + ")");

        String result = "";

        //Go through all the lines
        for (int i = 0; i < strLines.length; i++) {
            // Look for the tags
            Matcher tagsMatcher = betweenTags.matcher(strLines[i]);

            String tempLine = strLines[i];

            // Get all occurrences of given tags
            while (tagsMatcher.find()) {

               // String dataToSub = strLines[i].substring(startMatcher.end(), endMatcher.start());
                String dataToSub = tagsMatcher.group(2);

                //Check if we're supposed to do anything about this match
                if (data.containsKey(dataToSub)) {
                    tempLine = tempLine.replace(tagsMatcher.group(0),data.get(dataToSub));
                }
            }
            //Add line to result string
            result += tempLine;
            result += "\n";
        }
        return result;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void make() throws IOException {
        //All data is collected
        //Begin file processing

        //Specify template
        String pre = "<html>\n"+
                "\t<head>\n"+
                "\t\t<title>{%PROJECT_NAME%} {%DATE%}</title>\n"+
                "\n"+
                "\t\t<script>\n"+
                "\t\tfunction ShowHide(body_id){\n"+
                "\t\tvar TBody\n"+
                "\n"+
                "\t\tTBody = document.getElementById(body_id);\n"+
                "\n"+
                "\t\tif(!TBody) return true;\n"+
                "\n"+
                "\t\tif (TBody.style.display==\"none\") {\n"+
                "\t\t TBody.style.display=\"block\"\n"+
                "\t\t } \n"+
                "\t\telse {\n"+
                "\t\t TBody.style.display=\"none\"\n"+
                "\t\t }\n"+
                "\n"+
                "\t\treturn true;\n"+
                "\t\t}\n"+
                "\t\t</script>\n"+
                "\n"+
                "\n"+
                "\n"+
                "\t</head>\n"+
                "\n"+
                "\t<body>\n"+
                "\t\t<center><h1>{%PROJECT_NAME%}</h1></center>\n"+
                "\t\t<br />\n"+
                "\t\t<br />\n"+
                "\n"+
                "\t\t<center><h3>Activities</h3></center>\n"+
                "\t\t<br />\n"+
                "";
        String activityPart1 = "\t\t<table border=\"1\">\n" +
                "\t\t  <tr>\n" +
                "\t\t    <th colspan=\"2\">{%ACTIVITY_NAME%}</th>\n" +
                "\t\t  </tr>\n" +
                "\t\t  <tr>\n" +
                "\t\t    <td colspan=\"2\">Hours</td>\n" +
                "\t\t  </tr>\n" +
                "\t\t  <tr>\n" +
                "\t\t    <td>Expected</td>\n" +
                "\t\t    <td>{%ACTIVITY_EXP_HOURS%}</td>\n" +
                "\t\t  </tr>\n" +
                "\t\t  <tr>\n" +
                "\t\t    <td>Hours spent total</td>\n" +
                "\t\t    <td>{%ACTIVITY_HOURS_SPENT%}</td>\n" +
                "\t\t  </tr>\n" +
                "\t\t  <tr>\n" +
                "\t\t    <td>Hours spent past week</td>\n" +
                "\t\t    <td>{%ACTIVITY_HOURS_WEEK%}</td>\n" +
                "\t\t  </tr>\n" +
                "\n" +
                "\t\t</table>\n" +
                "\t\t  <table border =\"1\">\n" +
                "\n" +
                "\t\t  <tr><td><a href=\"#\" onClick=\"ShowHide('{%ACTIVITY_ID%}')\">Show employee data</a></td></tr>\n" +
                "\n" +
                "\t\t\t<tbody id = \"{%ACTIVITY_ID%}\" style=\"display: none;\">";
        String employeePart = "\t\t\t <tr>\n"+
                "\t\t\t <th>Employee name</th>\n"+
                "\t\t\t <th>Hours past week</th>\n"+
                "\t\t\t <th>Hours total</th>\n"+
                "\t\t\t </tr>\n"+
                "\t\t\t <tr>\n"+
                "\t\t\t <td>{%EMPLOYEE_NAME%}</td>\n"+
                "\t\t\t <td>{%EMP_HOURS_WEEK%}</td>\n"+
                "\t\t\t <td>{%EMP_TOTAL_HOURS%}</td>\n"+
                "\t\t\t </tr>\n"+
                "";
        String activityPart2 = "\t\t\t</tbody>\n"+
                "\n"+
                "\t\t </table>\n"+
                "\t\t <br />\n"+
                "";
        String post = "\t</body>\n"+
                "</html>";

        //Specify tags
        String[] tags = new String[]{"{%", "%}"};

        //Generate file
        PrintWriter writer = new PrintWriter(fileName + ".html", "UTF-8");

        //Make general data set
        Map<String, String> generalData = new HashMap<>();
        generalData.put("PROJECT_NAME", this.projectName);
        generalData.put("DATE", pms.getDate().toString());

        writer.println(subForData(pre, tags, generalData));
        for(int i = 0; i < project.getActivities().size(); i++) {
            ProjectActivity activity = project.getActivities().get(i);
            Map<String, String> activityData = new HashMap<>();
            activityData.put("ACTIVITY_NAME", activity.getActivityType());
            activityData.put("ACTIVITY_EXP_HOURS", "" + activity.getApproximatedHours());
            activityData.put("ACTIVITY_HOURS_SPENT", "" + (activity.getTotalRegisteredMinutes() / 60));
            activityData.put("ACTIVITY_HOURS_WEEK", "" + (activity.getTotalRegisteredMinutesPastWeek(pms.getDate()) / 60));
            activityData.put("ACTIVITY_ID", "" + i);

            writer.println(subForData(activityPart1, tags, activityData));

            //Write data for each employee on activity
            for (Employee employee : activity.getEmployees()) {
                Map<String, String> employeeData = new HashMap<>();
                String empName = employee.getFirstName() + " " + employee.getLastName();
                String empWorkThisWeek = "" + employee.getTotalRegisteredMinutesOnDayAndActivityPastWeek(pms.getDate(),activity);
                String empWorkTotal = "" + employee.getTotalRegisteredMinutesOnActivity(activity);
                employeeData.put("EMPLOYEE_NAME", empName);
                employeeData.put("EMP_HOURS_WEEK", empWorkThisWeek);
                employeeData.put("EMP_TOTAL_HOURS", empWorkTotal);

                writer.println(subForData(employeePart, tags, employeeData));
            }

            //Write data for each consultant on activity
            //TODO: This is the exact same as employee above
            for (Employee employee : activity.getConsultants()) {
                Map<String, String> employeeData = new HashMap<>();
                String empName = employee.getFirstName() + " " + employee.getLastName();
                String empWorkThisWeek = "" + employee.getTotalRegisteredMinutesOnDayAndActivityPastWeek(pms.getDate(),activity);
                String empWorkTotal = "" + employee.getTotalRegisteredMinutesOnActivity(activity);
                employeeData.put("EMPLOYEE_NAME", empName);
                employeeData.put("EMP_HOURS_WEEK", empWorkThisWeek);
                employeeData.put("EMP_TOTAL_HOURS", empWorkTotal);

                writer.println(subForData(employeePart, tags, employeeData));
            }

            writer.println(activityPart2);
        }


        writer.println(post);
        writer.close();
    }
}