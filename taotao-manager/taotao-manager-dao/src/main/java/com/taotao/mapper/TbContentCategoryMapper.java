package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbContentCategory;

public interface TbContentCategoryMapper {
   /**
    * 
    * 根据当前id查询内容
    * @param parentId
    * @return
    */
	
	List<TbContentCategory> getTbContentCategoryByParentId(long parentId);
	/**
	 * 
	 * 插入一天内容分类
	 * @param tbContentCategory
	 */
	void addTbContentCategory(TbContentCategory tbContentCategory);
	
	TbContentCategory querryById(long parentId); 
	
	void updateTbContentCategory(TbContentCategory tbContentCategory);
	
	
}