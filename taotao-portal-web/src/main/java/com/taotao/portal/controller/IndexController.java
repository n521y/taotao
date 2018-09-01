package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;

@Controller
public class IndexController {


	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private String AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private String AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private String AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private String AD1_WIDTH_B;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		//取轮播图的内容信息
		List<TbContent> contentList = contentService.getContentList(AD1_CID);
		//转换成Ad1NodeList
		List<Ad1Node> ad1List = new ArrayList<Ad1Node>();
		for (TbContent tbContent : contentList) {
			Ad1Node node = new Ad1Node();
			node.setAlt(tbContent.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			//添加到列表
			ad1List.add(node);
		}
		//把数据传递给页面。
		model.addAttribute("ad1", JsonUtils.objectToJson(ad1List));
		
		return "index";
	}


	
	
}
