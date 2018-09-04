package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
    /**
     *
     * 根据分类id查询指定下面是否有模板存在
     */
    TaotaoResult getItemParamByCid(long cId);
    /**
     *
     * 保存模板json数据到数据库中的模板表中
     * @param 需要保存的模板的json数据
     */
     TaotaoResult addItemParam(TbItemParam tbItemParam);

}
