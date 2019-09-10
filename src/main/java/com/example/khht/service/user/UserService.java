package com.example.khht.service.user;

import com.example.khht.dto.entity.user.User;
import com.example.khht.dto.user.UserLoginDTO;
import com.example.khht.dto.user.UserLoginInputDTO;
import com.example.khht.dto.user.UserRegisterInputDTO;
import com.example.khht.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public int insertUser(UserRegisterInputDTO userRegisterInputDTO){
        return userMapper.insertUser(userRegisterInputDTO);
    }

    public UserLoginDTO selectUserByEmailAndPassword(UserLoginInputDTO userLoginInputDTO){
        return userMapper.selectUserByEmailAndPassword(userLoginInputDTO);
    }

    public int selectCountUserEmail(String userEmail){
        return userMapper.selectCountUserEmail(userEmail);
    }

    public int selectCountUserName(String userName){
        return userMapper.selectCountUserName(userName);
    }

    public String selectUserNameByUserId(Integer userId){
        return userMapper.selectUserNameByUserId(userId);
    }

    public void updateFileIdByUserId(User user){
        userMapper.updateFileIdByUserId(user);
    }
}