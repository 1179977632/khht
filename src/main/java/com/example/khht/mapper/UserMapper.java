package com.example.khht.mapper;

import com.example.khht.dto.entity.user.User;
import com.example.khht.dto.user.UserLoginDTO;
import com.example.khht.dto.user.UserLoginInputDTO;
import com.example.khht.dto.user.UserRegisterInputDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insertUser(UserRegisterInputDTO userRegisterInputDTO);

    UserLoginDTO selectUserByEmailAndPassword(UserLoginInputDTO userLoginInputDTO);

    int selectCountUserEmail(String userEmail);

    int selectCountUserName(String userName);

    String selectUserNameByUserId(Integer userId);

    void updateFileIdByUserId(User user);

}