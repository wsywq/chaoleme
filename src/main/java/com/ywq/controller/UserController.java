package com.ywq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ywq.common.ResponseTemplate;
import com.ywq.entity.User;
import com.ywq.service.UserService;
import com.ywq.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public ResponseTemplate<String> sendMsg(@RequestBody User user, HttpSession httpSession) {
        log.info("User send msg {}", user.toString());
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = {}", code);
            httpSession.setAttribute(phone, code);
            //SMSUtils.sendMessage("waimai","code",phone,code);
            return ResponseTemplate.success("Send successfully");
        }
        return ResponseTemplate.error("Send failed");
    }

    @PostMapping("/login")
    public ResponseTemplate<User> login(@RequestBody Map map, HttpSession session) {
        log.info("login {}", map.toString());
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        String codeInSession = (String) session.getAttribute(phone);
        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return ResponseTemplate.success(user);
        }
        return ResponseTemplate.error("Login failed");
    }

    @GetMapping("/try")
    public ResponseTemplate<String> tryException(){
        System.out.println("test"+ 1/0);
        return ResponseTemplate.success("Success");
    }
}
