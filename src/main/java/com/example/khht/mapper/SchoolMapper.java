package com.example.khht.mapper;

import com.example.khht.dto.entity.school.School;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolMapper {

    List<School> selectAll();

}
