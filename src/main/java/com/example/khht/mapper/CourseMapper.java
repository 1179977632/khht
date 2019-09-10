package com.example.khht.mapper;

import com.example.khht.dto.entity.course.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper {

    List<Course> selectAll();

}
