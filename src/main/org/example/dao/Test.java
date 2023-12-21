package main.org.example.dao;

import main.org.example.model.Employee;
import main.org.example.model.Role;
import main.org.example.model.Task;
import main.org.example.model.User;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        PassportDAO passportDAO = new PassportDAO();
        OfficeDAO officeDAO = new OfficeDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        TaskDAO taskDAO = new TaskDAO();
        RoleDAO roleDAO = new RoleDAO();
        UserDAO userDAO = new UserDAO();
        //List<Employee> employees = employeeDAO.findAll();

        User user = userDAO.findByEmail("zarkonmen@gmail.com");

        System.out.println(user);

        user.setIsActive(true);

        System.out.println(user);

        userDAO.activated(user);

//        Employee employee = employeeDAO.findById(1);
//        Set<Task> tasks = employee.getTasks();
//        tasks.add(taskDAO.findById(1));
//        employee.setTasks(tasks);
//        employeeDAO.update(employee);

//        for (Employee employee : employees){
//            System.out.println(employee.getOffice());
//            System.out.println(employee.getPassport());
//        }

//        Task task = new Task();
//        task.setDescription("Fix");
//        task.setStatus("In progress");
//        task.setPriority("Critical");
//        task.setDeadline(new Date(new java.util.Date().getTime()));
//
//        taskDAO.create(task);
    }
}
