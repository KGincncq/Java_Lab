package com.sso.login.controller;

import com.sso.login.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.sso.login.pojo.DB;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import  com.sso.login.pojo.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    public String doLogin(User user, HttpSession session, HttpServletResponse response) {
        String target=(String) session.getAttribute("target");
        Optional<User> first=DB.dbusers.stream().filter(dbUser ->dbUser.getUsername().equals(user.getUsername())&&dbUser.getPassword().equals(user.getPassword())).findFirst();
        if(first.isPresent()){
            String token= UUID.randomUUID().toString();
            Cookie cookie=new Cookie("TOKEN",token);
           cookie.setDomain("localhost");
            response.addCookie(cookie);
            LoginCacheUtil.loginUser.put(token,first.get());
        }else {
            session.setAttribute("msg","用户名密码错误");
            return  "login";
        }
        return "redirect:"+target;
    }

    @GetMapping("info")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token){
        if(!StringUtils.isEmpty(token)){
            User user =LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        }else {
            return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}


































