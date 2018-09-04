package com.taotao.mapper;


import com.taotao.pojo.TbItemParamItem;

public interface TbItemParamItemMapper {
    /**
     *
     * 把规格参数保存到数据库中
     * @param tbItemParamItem  需要保存的规格参数
     */

    void insertTbitemParamItem(TbItemParamItem tbItemParamItem);

    TbItemParamItem  getItemParamItemByItemId(long itemId);
}