package dk.dtu.software.group8;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public List<Employee> employees;

    public DatabaseManager() {
        loadEmployees("Employees.txt");
    }

    public DatabaseManager(String fileName) {
        loadEmployees(fileName);
    }
	
	public List<Employee> getEmployees() {
        return employees;
	}


    public void loadEmployees(String fileName) {

        employees = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("Employees.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                //The database has the employees as id;firstname;lastname.
                String[] employeeCreds = line.split(";");

                employees.add(new Employee(employeeCreds[0], employeeCreds[1], employeeCreds[2]));
            }

        } catch (IOException e){
            //TODO: Later on we should handle this differently. It should throw an exception and we should test for it.
            System.out.println("Database couldn't be loaded");
        }


    }
	
}
