package com.example.khht.controller.course;

import com.example.khht.dto.entity.JSONResult;
import com.example.khht.dto.entity.course.Course;
import com.example.khht.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/course/*")
public class CourseController {

    @SuppressWarnings("all")
    @Autowired
    CourseService courseService;

    @GetMapping("list")
    public JSONResult nameList() {
        JSONResult jsonResult = new JSONResult();
        List<Course> list = courseService.selectAll();
        jsonResult.setData(list);
        return jsonResult;
    }

    /*@GetMapping("into")
    public String intoCourse(String userEmail,String courseName){
        WebSocketServer.intoCourse(userEmail,courseName);
        return "ok";
    }
    @GetMapping("quit")
    public String quitCourse(String userEmail,String courseName){
        WebSocketServer.quitCourse(userEmail,courseName);
        return "ok";
    }*/

    /*@GetMapping("getid/{courseName}")
    public String getCourseId(@PathVariable("courseName") String coursename){
        return courseMapper.searchByCourseName(coursename).toString();
    }*/
}