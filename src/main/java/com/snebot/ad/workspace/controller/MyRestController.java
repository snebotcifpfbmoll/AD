package com.snebot.ad.workspace.controller;

import com.snebot.ad.workspace.data.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MyRestController {
    @GetMapping("/getStudent")
    public Student getStudent(@RequestParam(value = "name", defaultValue = "unknown") String name,
                              @RequestParam(value = "age", defaultValue = "0") Integer age,
                              @RequestParam(value = "mark", defaultValue = "0") Float mark) {
        return new Student(name, age, mark);
    }

    @GetMapping("/createStudents")
    public List<Student> createStudents(@RequestParam(value = "q", defaultValue = "1") Integer quantity) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Student student = new Student(String.format("Student %d", i), 0, (float)0.0);
            students.add(student);
        }
        return students;
    }

    @GetMapping("/createStudents/{q}")
    public List<Student> createStudentsWithPathVariable(@PathVariable(value = "q") Integer quantity) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Student student = new Student(String.format("Student %d", i), 0, (float)0.0);
            students.add(student);
        }
        return students;
    }
}
