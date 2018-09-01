package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {
	
	
	public List<EasyUITreeNode> getContentCategoryList(long parentId);
    /**
     * 添加内容信息
     * 
     */
	
	TaotaoResult addContentCategory(long parentId, String name);
	
	
	
	
}
