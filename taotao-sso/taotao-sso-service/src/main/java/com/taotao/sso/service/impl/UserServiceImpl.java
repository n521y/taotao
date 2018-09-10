package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${SESSION_EXPIRE}")
    private int SESSION_EXPIRE;
    @Override
    public TaotaoResult checkData(String param, int type) {
        if(type==1){
            TbUser tbUser= userMapper.getTbUserByUserName(param);
            if(tbUser!=null){
                return  TaotaoResult.ok(false);
            }
        }else if(type==2){
            TbUser tbUser = userMapper.getUserByPhone(param);
            if(tbUser!=null){
                return  TaotaoResult.ok(false);
            }

        }else if(type==3){
            TbUser tbUser = userMapper.getUserByEmail(param);
            if(tbUser!=null){
                return  TaotaoResult.ok(false);
            }

        }else{

            return  TaotaoResult.build(500,"ok","传入的参数类型不合法");
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        // 1、使用TbUser接收提交的请求。

        if (StringUtils.isBlank(user.getUserName())) {
            return TaotaoResult.build(400, "用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassWord())) {
            return TaotaoResult.build(400, "密码不能为空");
        }
        //校验数据是否可用
        TaotaoResult result = checkData(user.getUserName(), 1);
        if (!(boolean) result.getData()) {
            return TaotaoResult.build(400, "此用户名已经被使用");
        }
        //校验电话是否可以
        if (StringUtils.isNotBlank(user.getPhone())) {
            result = checkData(user.getPhone(), 2);
            if (!(boolean) result.getData()) {
                return TaotaoResult.build(400, "此手机号已经被使用");
            }
        }
        //校验email是否可用
        if (StringUtils.isNotBlank(user.getEmail())) {
            result = checkData(user.getEmail(), 3);
            if (!(boolean) result.getData()) {
                return TaotaoResult.build(400, "此邮件地址已经被使用");
            }
        }
        // 2、补全TbUser其他属性。
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // 3、密码要进行MD5加密。
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassWord().getBytes());
        user.setPassWord(md5Pass);
        // 4、把用户信息插入到数据库中。
        userMapper.insert(user);
        // 5、返回TaotaoResult。
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
        if (StringUtils.isBlank(username)){
            return TaotaoResult.build(400, "用户名不能为空");
        }
        if (StringUtils.isBlank(password)){
            return TaotaoResult.build(400, "密码不能为空");
        }
        TbUser tbUser= userMapper.getUserByLogin(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if(tbUser==null){
            return  TaotaoResult.build(400,"账号密码有误重新输入！");
        }
        String uuid= UUID.randomUUID().toString();
        String token=uuid.replace("-","");
        tbUser.setPassWord("null");
        jedisClient.set(USER_INFO+":"+token, JsonUtils.objectToJson(tbUser));
        jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
        return TaotaoResult.ok(token);

    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_INFO + ":" + token);
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(tbUser);
    }

}

