package com.demo.service;

import java.util.List;
import java.util.Optional;

import com.demo.entity.Student;

public interface StudentService {
    Student saveStudent(Student student);
    Optional<Student> getStudentById(Long id);
    List<Student> getAllStudents();    
}
