package com.example.khht.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.example.khht.dto.entity.user.User;
import com.example.khht.dto.user.UserLoginDTO;
import com.example.khht.dto.user.UserLoginInputDTO;
import com.example.khht.dto.entity.JSONResult;
import com.example.khht.dto.user.UserRegisterInputDTO;
import com.example.khht.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user/*")
public class UserController {

    @SuppressWarnings("all")
    @Autowired
    UserService userService;

    @GetMapping("test")
    public JSONResult test() {
        JSONResult<Integer> jsonResult = new JSONResult<>();
        return jsonResult;
    }

    @PostMapping("register")
    public JSONResult register(@RequestBody JSONObject jsonObject) {
        JSONResult jsonResult = new JSONResult();
        UserRegisterInputDTO userRegisterInputDTO = JSONObject.parseObject(jsonObject.toJSONString(),UserRegisterInputDTO.class);
        int num = userService.insertUser(userRegisterInputDTO);
        if(num==1){
            jsonResult.setMessage("注册成功");
            return jsonResult;
        }else {
            jsonResult.setMessage("注册失败");
            return jsonResult;
        }
    }

    @GetMapping("isexistemail/{userEmail}")
    public JSONResult isExistUserEmail(@PathVariable("userEmail") String userEmail) {
        JSONResult<Integer> jsonResult = new JSONResult<>();
        int a = userService.selectCountUserEmail(userEmail);
        if(a==0){
            jsonResult.setMessage("邮箱未注册");
            return jsonResult;
        }else {
            jsonResult.setMessage("邮箱已注册");
            return jsonResult;
        }
    }

    @GetMapping("isexistname/{userName}")
    public JSONResult isExistUserName(@PathVariable("userName") String userName) {
        JSONResult<Integer> jsonResult = new JSONResult<>();
        int a = userService.selectCountUserName(userName);
        if(a==0){
            jsonResult.setMessage("呢称未存在");
            return jsonResult;
        }else {
            jsonResult.setMessage("呢称已存在");
            return jsonResult;
        }
    }

    @PostMapping("login")
    public JSONResult login(@RequestBody JSONObject jsonObject) {
        JSONResult<UserLoginDTO> jsonResult = new JSONResult<>();
        UserLoginInputDTO userLoginInputDTO = JSONObject.parseObject(jsonObject.toJSONString(),UserLoginInputDTO.class);
        UserLoginDTO userLoginDTO = userService.selectUserByEmailAndPassword(userLoginInputDTO);
        /*if (token == null) {
        , HttpServletResponse response, @CookieValue(value = "token", required = false) String token
            CookieUtils.writeCookie(response, "token", TOKENX);
        } else {
            System.out.println(token);
        }*/
        if(userLoginDTO !=null){
            jsonResult.setData(userLoginDTO);
            jsonResult.setMessage("登录成功");
            return jsonResult;
        }else {
            jsonResult.setMessage("登录失败");
            return jsonResult;
        }
    }

    @GetMapping("getname/{userId}")
    public JSONResult getUserName(@PathVariable("userId") Integer userId) {
        JSONResult<String> jsonResult = new JSONResult<>();
        jsonResult.setData(userService.selectUserNameByUserId(userId));
        return jsonResult;
    }

    @GetMapping("fileid")
    public JSONResult updateFileIdByUserId(@RequestParam Integer userId,@RequestParam Integer fileId) {
        JSONResult<String> jsonResult = new JSONResult<>();
        User user = new User();
        user.setUserId(userId);
        user.setFileId(fileId);
        userService.updateFileIdByUserId(user);
        return jsonResult;
    }

    /*@GetMapping("list")
    public List<user> list() {
        return userMapper.listSample();
    }

    @GetMapping("list/{username}")
    public List<user> listByUsername(@PathVariable("username") String username) {
        return userMapper.listByUsername(username);
    }*/

/*    @PostMapping("login")
    public User login(@RequestBody JSONObject jsonObject) {
        User user = new User(jsonObject.getString("userEmail"),jsonObject.getString("userPassword"),null,null,null);
        return userMapper.loginByEmailAndPassword(user.getUserEmail(),user.getUserPassword());
    }*/

/*    @PostMapping("register")
    public User register(@RequestBody JSONObject jsonObject) {
        User user = new User();
        user = JSONObject.parseObject(jsonObject.toJSONString(),User.class);
        //User user = new User(jsonObject.getString("userEmail"),jsonObject.getString("userPassword"),jsonObject.getString("userName"),jsonObject.getString("schoolName"),jsonObject.getString("userSex"));
        if(userMapper.searchByEmail(user.getUserEmail())!=null){
            user.setUserName(null);
            user.setSchoolName("邮箱已存在！");
            return user;
        }else if(userMapper.searchByUserName(user.getUserName())!=null){
            user.setUserName(null);
            user.setSchoolName("呢称已存在！");
            return user;
        }else if(userMapper.searchBySchoolName(user.getSchoolName())!=null){
            userMapper.register(user);
            return user;
        }
        else {
            user.setUserName(null);
            user.setSchoolName("app暂没有该学校！");
            return user;
        }
    }*/

    /*@GetMapping("get/{username}/{password}")
    public user get(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.get(username, password);
    }

    @GetMapping("set/{userId}/{username}/{password}")
    public List<user> set(@PathVariable("userId") String userId, @PathVariable("username") String username, @PathVariable("password") String password) {
        userMapper.set(userId,username, password);
        return userMapper.listSample();
    }

    @GetMapping("update")
    public List<user> updateUser() {
        user user = new user("123","小强","123","4631354");
        userMapper.update(user);
        return userMapper.listSample();
    }

    @GetMapping("delete/{userId}")
    public List<user> deleteById(@PathVariable("userId") String userId) {
        userMapper.deleteById(userId);
        return userMapper.listSample();
    }

    @GetMapping("get/bad/{username}/{password}")
    public user getBadUser(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.getBadUser(username, password);
    }*/

}