package com.demo.restcrud;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import java.util.Optional;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.controller.StudentController;
import com.demo.entity.Student;
import com.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveStudent() throws Exception {
        Student student = new Student(3L, "John", "Doe", "john.doe@example.com");

        Student savedStudent = new Student(3L, "John", "Doe", "john.doe@example.com");

        when(studentService.saveStudent(student)).thenReturn(savedStudent);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(savedStudent)));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Long studentId = 1L;
        Student student = new Student(studentId, "John", "Doe", "john.doe@example.com");

        when(studentService.getStudentById(studentId)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/api/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(student)));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> students = List.of(
                new Student(1L, "John", "Doe", "john.doe@example.com"),
                new Student(2L, "Jane", "Doe", "jane.doe@example.com")
        );

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(students)));
    }
}


