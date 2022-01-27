package com.imooc.service.impl;

import com.imooc.mapper.TeacherMapper;
import com.imooc.pojo.Teacher;
import com.imooc.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void save(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    @Override
    public Teacher findById(String id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherMapper.selectAll();
    }

    @Override
    public void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }

    @Override
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }
}
