package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

    public TaotaoResult checkData(String param, int type);

    public TaotaoResult createUser(TbUser user);

    public TaotaoResult login(String username, String password);

    TaotaoResult getUserByToken(String token);

}
