package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
@Service
public class ContentCategoryServiceImpl  implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		List<TbContentCategory> contentCategories=tbContentCategoryMapper.getTbContentCategoryByParentId(parentId);
		List<EasyUITreeNode> result=new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tbContentCategory : contentCategories) {
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName()); 
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			result.add(node);
		}
		
		return result;
	}
	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		        // 1、接收两个参数：parentId、name
				// 2、向tb_content_category表中插入数据。
				// a)创建一个TbContentCategory对象
				TbContentCategory tbContentCategory = new TbContentCategory();
				// b)补全TbContentCategory对象的属性
				tbContentCategory.setIsParent(false);
				tbContentCategory.setName(name);
				tbContentCategory.setParentId(parentId);
				//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
				tbContentCategory.setSortOrder(1);
				//状态。可选值:1(正常),2(删除)
				tbContentCategory.setStatus(1);
				tbContentCategory.setCreated(new Date());
				tbContentCategory.setUpdated(new Date());
				tbContentCategoryMapper.addTbContentCategory(tbContentCategory);
				TbContentCategory parentContentCategory=tbContentCategoryMapper.querryById(parentId);
				if(!parentContentCategory.getIsParent()) {
					parentContentCategory.setIsParent(true);
					tbContentCategoryMapper.updateTbContentCategory(parentContentCategory);
					
					
				}
				return TaotaoResult.ok(tbContentCategory);

	}

}
