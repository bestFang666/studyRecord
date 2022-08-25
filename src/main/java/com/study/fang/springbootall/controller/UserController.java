package com.study.fang.springbootall.controller;

import com.study.fang.springbootall.pojo.User;
import com.study.fang.springbootall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bestFang666
 * @date 2022/8/25 10:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAllUser")
    public Map<String, Object> getAllUsers() {
        List<User> list = userService.list();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list",list);
        return map;
    }

    @PostMapping("/saveUser")
    public Map<String,Object> saveUser( @RequestBody User user){
        userService.save(user);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","插入成功");
        map.put("code",200);
        return map;
    }

    @PostMapping("/deleteUserByID")
    public Map<String, Object> deleteUserByID(@RequestParam Integer id){
        userService.removeById(id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","删除成功");
        map.put("code",200);
        return map;
    }

    @PostMapping("/updateUser")
    public Map<String, Object> updateUser(@RequestBody User user){
        userService.updateById(user);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","修改成功");
        map.put("code",200);
        return map;
    }

}
