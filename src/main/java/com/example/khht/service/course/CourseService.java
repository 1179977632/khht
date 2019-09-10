package com.example.khht.service.course;

import com.example.khht.dto.entity.course.Course;
import com.example.khht.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseMapper courseMapper;

    public List<Course> selectAll(){
        return courseMapper.selectAll();
    }
}
