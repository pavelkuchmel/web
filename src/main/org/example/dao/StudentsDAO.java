package main.org.example.dao;

import main.org.example.model.Student;

public class StudentsDAO extends AbstractJpaDAO<Integer, Student> {


    public static void main(String[] args) {
        StudentsDAO studentsDAO = new StudentsDAO();
        System.out.println(studentsDAO.findById(2));
    }
}
