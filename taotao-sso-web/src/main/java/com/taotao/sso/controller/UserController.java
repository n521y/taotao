package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${COOKIE_TOKEN_KEY}")
    private  String COOKIE_TOKEN_KEY;

    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type){
        TaotaoResult result = userService.checkData(param, type);
        return  result;
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user) {
        TaotaoResult result = userService.createUser(user);
        return result;
    }


    @RequestMapping(value="/login", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password,
                              HttpServletRequest request, HttpServletResponse response) {
        // 1、接收两个参数。
        // 2、调用Service进行登录。
        TaotaoResult result = userService.login(username, password);
        // 3、从返回结果中取token，写入cookie。Cookie要跨域。
        CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, result.getData().toString());
        // 4、响应数据。Json数据。TaotaoResult，其中包含Token。
        return result;

    }


    @RequestMapping("/token/{token}")
    @ResponseBody
    public TaotaoResult getUserByToken(@PathVariable String token) {
        TaotaoResult result = userService.getUserByToken(token);
        return result;
    }


}
