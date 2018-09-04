package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

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
	TaotaoResult addItem(TbItem item, String desc,String itemParams);

	TbItemDesc getItemDescById(long itemId);

	/**
	 * 根据商品id查询商品规格参数 并且把规格参数转化为html页面 通过model添加到域对象
	 * @param itemId
	 * @return html填充了规格参数
	 */
	String getItemParamItemByItemId(long itemId );
	
}
