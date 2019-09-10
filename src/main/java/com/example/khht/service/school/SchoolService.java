package com.example.khht.service.school;

import com.example.khht.dto.entity.school.School;
import com.example.khht.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    @Autowired
    SchoolMapper schoolMapper;

    public List<School> selectAll(){
        return schoolMapper.selectAll();
    }

}
