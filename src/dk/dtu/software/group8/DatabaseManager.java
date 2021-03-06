package dk.dtu.software.group8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcus
 */
public class DatabaseManager {

    public List<Employee> employees;

    /**
     * Created by Marcus
     */
    public DatabaseManager(String fileName) throws IOException {
        loadEmployees(fileName);
    }

    /**
     * Created by Morten
     */
	public List<Employee> getEmployees() {
        return employees;
	}

    /**
     * Created by Tobias
     */
    public void loadEmployees(String fileName) throws IOException {
        employees = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = br.readLine()) != null) {
            //The database has the employees as id;firstname;lastname.
            String[] employeeCreds = line.split(";");

            employees.add(new Employee(employeeCreds[0], employeeCreds[1], employeeCreds[2]));
        }
    }
}