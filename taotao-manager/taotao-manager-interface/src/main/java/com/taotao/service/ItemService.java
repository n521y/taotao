package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

	/**
	 * 
	 * 根据商品id查询指定的商品
	 * 
	 */
	TbItem geTbItemById(long itemId);
	
	
	EasyUIDataGridResult getItems(int page, int rows);
	
	
	/**
	 * 
	 * 添加商品的描述信息 商品的规格参数
	 */
	TaotaoResult addItem(TbItem item, String desc);
	
}
