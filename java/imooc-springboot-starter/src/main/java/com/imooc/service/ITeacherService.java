package com.imooc.service;

import com.imooc.pojo.Teacher;

import java.util.List;

public interface ITeacherService {
    /**
     * 保存Teacher到数据库
     * @param teacher 老师实体
     */
    void save(Teacher teacher);

    /**
     * 通过Id找到Teacher
     * @param id 老师id
     * @return 老师实体
     */
    Teacher findById(String id);

    /**
     * 查询所有Teacher
     * @return 老师实体集合
     */
    List<Teacher> getAll();

    /**
     * 更新Teacher到数据库
     * @param teacher 老师实体
     */
    void update(Teacher teacher);

    /**
     * 删除指定id的Teacher
     * @param id 老师id
     */
    void delete(String id);
}
