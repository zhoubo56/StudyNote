package com.imooc.controller;

import com.imooc.pojo.Teacher;
import com.imooc.pojo.bo.TeacherBO;
import com.imooc.service.ITeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("teachers")
public class TeacherController {
    @Autowired
    private ITeacherService iTeacherService;

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable String id) {
        var teacher = iTeacherService.findById(id);
        return teacher == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(teacher);
    }

    @GetMapping
    public ResponseEntity getAll() {
        var list = iTeacherService.getAll();
        return list.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TeacherBO teacherBO) throws URISyntaxException {
        var tId = UUID.randomUUID().toString();
        var teacher = new Teacher();
        BeanUtils.copyProperties(teacherBO, teacher);
        teacher.setId(tId);
        iTeacherService.save(teacher);
        var uri = new URI("teachers/" + tId);
        return ResponseEntity.created(uri).body(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        var teacher = iTeacherService.findById(id);
        if (teacher == null) {
            return ResponseEntity.notFound().build();
        }

        iTeacherService.delete(id);
        return ResponseEntity.ok(teacher);
    }
}
