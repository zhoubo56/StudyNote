package com.imooc.controller;

import com.imooc.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@RestController
@RequestMapping("students")
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ArrayList<Student> students = new ArrayList<>();

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable("id") int id) {
        if (id < 0 || id > students.size() - 1) {
            return ResponseEntity.notFound().build();
        }
        var student = students.get(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Student>> getAll(@RequestParam(required = false) String name) { //RequestParam 如果参数名称保持一直，注解可以省略
        logger.info("getAll -> name:{}", name);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Student student,
                               @RequestHeader("token") String token) throws URISyntaxException {
        logger.info("post -> token:{}, student.name:{}", token, student.name);
        if (!StringUtils.hasText(student.name)) {
            return ResponseEntity.noContent().build();
        }
        students.add(student);
        var id = students.size() - 1;
        var uri = new URI("students/" + id);
        return ResponseEntity.created(uri).body(student);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patch(@PathVariable("id") int id,
                                         @RequestBody() Student student) {
        if (id < 0 || id > students.size() - 1) {
            return ResponseEntity.notFound().build();
        }
        var oldStudent = students.get(id);
        if (oldStudent != null) {
            if (student.name != null) oldStudent.name = student.name;
            if (student.age > 0) oldStudent.age = student.age;
            students.set(id, oldStudent);
        }

        return ResponseEntity.ok(oldStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> delete(@PathVariable("id") int id) {
        if (id < 0 || id > students.size() - 1) {
            return ResponseEntity.notFound().build();
        }
        var student = students.remove(id);
        return ResponseEntity.ok(student);
    }
}
