package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface TbItemMapper {
	
	/**
	 * 根据商品id查询指定商品信息
	 * 
	 * @param itemId 商品id
	 * @return  查询的商品信息
	 */
	
	

	TbItem getItemById(long itemId);
	
	List<TbItem> getTbItem();
	
	/**
	 * 添加上平基本信息
	 * 
	 */
	 void addTbItem(TbItem item);
	
	 /**
	  * 添加商品描述信息
	  * 
	  * 
	  */
	 void addTbItemDesc(TbItemDesc itemDesc);
	 
	 
}