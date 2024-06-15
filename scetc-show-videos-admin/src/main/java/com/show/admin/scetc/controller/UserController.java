package com.show.admin.scetc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.show.admin.scetc.pojo.UserConsole;
import com.show.admin.scetc.pojo.Users;
import com.show.admin.scetc.service.UserService;
import com.show.admin.scetc.utils.LybJsonResult;
import com.show.admin.scetc.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/User")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/all")
    public List<Users> getAllUser(){
        List<Users> list = userService.list();
        return list;
    }
    @DeleteMapping("/{id}")
    public LybJsonResult delete(@PathVariable String id) {
        boolean flag  = userService.remove(id);
        System.out.println(flag);
        if (flag) {
            return LybJsonResult.ok();
        }else return LybJsonResult.errorException("删除失败");
    }
    @PutMapping("/status")
    public LybJsonResult updateStatus(@RequestBody Users user){
        String id = user.getId();
        Integer status = user.getStatus();
        Boolean flag = userService.update1(id, status);
        if (flag) {
            return LybJsonResult.ok();
        }else return LybJsonResult.errorException("状态切换失败");
    }
    @PutMapping("/update")
    public LybJsonResult updateUser(@RequestBody Users user){
        log.info(user.toString());
        //获取密码
        String password = user.getPassword();
        System.out.println(password);
            try {
                password = MD5Utils.getMD5Str(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        log.info("修改密码加密：{}",password);
        Boolean flag = userService.update(user.getId(),password);
        if (flag) {
            log.info("flag{}",flag);
            return LybJsonResult.ok();
        }else return LybJsonResult.errorException("密码修改失败");
    }
    @GetMapping("/getUserConsole")
    public List<UserConsole> getUserConsole(){
        List<UserConsole> list = userService.getUserConsole();
        return list;
    }
}
