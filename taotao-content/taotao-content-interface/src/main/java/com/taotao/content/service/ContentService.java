package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	/**
	 * 
	 *根据分类id查询相应分类下的所有信息
	 * @param categoryId  分类id
	 * @return  jsion 对象
	 */
	
	EasyUIDataGridResult findContentAll(long categoryId);

	
	/**
	 * 
	 * 
	 * @param tbContent 要增加的信息
	 * @return  返回给页面的jsion对象
	 */
	TaotaoResult addContent(TbContent tbContent);
	
	
	
	List<TbContent> getContentList(long categoryId);
}
