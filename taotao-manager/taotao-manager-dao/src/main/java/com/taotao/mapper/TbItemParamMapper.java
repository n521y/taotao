package com.taotao.mapper;


import com.taotao.pojo.TbItemParam;

public interface TbItemParamMapper {
    /**
     * 根据分类Id查询相应类别下的规格参数模板表
      * @param cId
     * @return
     */
     TbItemParam getItemParamByCid(long itemCatId);

    /**
     *把商品规格参数模板对象存入数据库中
     *
     * @param tbItemParam 商品规格模板对象
     */
     void addItemParam(TbItemParam tbItemParam);
}