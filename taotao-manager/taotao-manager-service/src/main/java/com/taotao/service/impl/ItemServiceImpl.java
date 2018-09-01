package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
	private TbItemMapper tbItemMapper;
    
    
	@Override
	public TbItem geTbItemById(long itemId) {
		TbItem item= tbItemMapper.getItemById(itemId);
		return item;
	}


	@Override
	public EasyUIDataGridResult getItems(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//得到所有 商品信息
		List<TbItem> list = tbItemMapper.getTbItem();
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		//创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}


	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		long itemId=IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte) 1);
		Date date=new Date();
		item.setCreated(date);
		item.setUpdated(date);
		tbItemMapper.addTbItem(item);
		//补全商品的描述信息
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		tbItemMapper.addTbItemDesc(itemDesc);
		return TaotaoResult.ok();
		

	}


	

	


	

}
