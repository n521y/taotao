package com.taotao.controller;

import com.taotao.pojo.TbItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		//根据商品id查询商品信息
		TbItem tbItem = itemService.geTbItemById(itemId);
		return tbItem;
	}
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows) {
    	EasyUIDataGridResult result = itemService.getItems(page, rows);
		return result;
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult getItem(TbItem item,String desc,String itemParams) {
    	TaotaoResult result = itemService.addItem(item, desc,itemParams);
		return result;
    }
    
	
}
