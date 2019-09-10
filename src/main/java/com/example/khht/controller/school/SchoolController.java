package com.example.khht.controller.school;

import com.example.khht.dto.entity.JSONResult;
import com.example.khht.dto.entity.school.School;
import com.example.khht.service.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/school/*")
public class SchoolController {

    @SuppressWarnings("all")
    @Autowired
    SchoolService schoolService;

    @GetMapping("list")
    public JSONResult nameList() {
        JSONResult jsonResult = new JSONResult();
        List<School> list = schoolService.selectAll();
        jsonResult.setData(list);
        return jsonResult;
    }

}
