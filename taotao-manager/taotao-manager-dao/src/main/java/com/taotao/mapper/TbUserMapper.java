package com.taotao.mapper;


import com.taotao.pojo.TbUser;

public interface TbUserMapper {
    TbUser getTbUserByUserName(String userName);

    TbUser getUserByPhone(String phone);

    TbUser getUserByEmail(String email);

    //创建一个用户
    void insert(TbUser tbUser);

    TbUser getUserByLogin(String userName,String passWord);
}