package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.utils.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class ViewController {
    @GetMapping("/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "") String target, HttpSession session, @CookieValue(required = false,value ="TOKEN")Cookie cookie){
        if(StringUtils.isEmpty(target)){
            target="http://localhost:9010/view/index";
        }
        if(cookie!=null) {
            String value = cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(value);
            if (user != null) {
                return "redirect:" + target;
            }
        }
        //TODO 做地址是否合法的校验
        session.setAttribute("target",target);
        return "login";
    }
}
