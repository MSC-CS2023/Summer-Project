package uk.gigbookingapp.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.gigbookingapp.backend.entity.User;
import uk.gigbookingapp.backend.mapper.UserMapper;
import uk.gigbookingapp.backend.utils.JwtUtils;
import uk.gigbookingapp.backend.utils.Result;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public Result login(String id, String password){

        User user = userMapper.selectById(id);
        if (user == null){
            return Result.error().setMessage("ID does not exist.");
        } else if (user.getPassword().compareTo(password) != 0){
            return Result.error().setMessage("Password is wrong.");
        }
        String token = JwtUtils.generateToken(user);
        return Result.ok().data("user", user).data("token", token);
    }
}