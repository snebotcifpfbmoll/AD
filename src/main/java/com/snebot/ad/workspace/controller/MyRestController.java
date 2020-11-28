package com.snebot.ad.workspace.controller;

import com.snebot.ad.workspace.data.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/menu", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> menu() {
        try {
            Map<String, Object> map = new HashMap<>();
            String[] entrantes = {"Carpaccio", "Queso", "Sopa", "Pan de ajo"};
            map.put("Entrantes", CollectionUtils.arrayToList(entrantes));
            String[] pizzas = {"Margarita", "Prosciutto", "Hawaiana", "Funghi", "Calzone"};
            map.put("Pizzas", CollectionUtils.arrayToList(pizzas));
            String[] bebidas = {"Coca-Cola", "Fanta", "Agua", "Zumo"};
            map.put("Bebidas", CollectionUtils.arrayToList(bebidas));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
