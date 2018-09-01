package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import javax.xml.ws.soap.Addressing;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
@Service
public class ContentServiceImpl implements ContentService {

	@Value("CONTENT_KEY")
	private String CONTENT_KEY;
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	public TbContentMapper tbContentMapper;
	
	@Override
	public EasyUIDataGridResult findContentAll(long categoryId) {
		
		List<TbContent> tbContents = tbContentMapper.findTbContentById(categoryId);
		EasyUIDataGridResult result =new EasyUIDataGridResult();
		result.setTotal(tbContents.size());
		result.setRows(tbContents);
		
		return result;
	}

	@Override
	public TaotaoResult addContent(TbContent tbContent) {
		
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		
		tbContentMapper.addTbContent(tbContent);
		jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId()+"");
		
		return TaotaoResult.ok();

	}

	
	
	@Override
	public List<TbContent> getContentList(long categoryId) {
		/**
		 * 
		 * 这里取缓存
		 */
		
		try {
			String json = jedisClient.hget(CONTENT_KEY, categoryId+"");
			//判断json是否为空
			if (StringUtils.isNotBlank(json)) {
				//把json转换成list
				System.out.println("从缓存中取出数据");
				List<TbContent> result = JsonUtils.jsonToList(json, TbContent.class);
				System.out.println("从缓存取数据");
				return result;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		List<TbContent> result = tbContentMapper.findTbContentById(categoryId);
		System.out.println("查询数据库");
		//向缓存添加数据
		try {
			jedisClient.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(result));
			System.out.println("加入缓存");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return result;
	}
	
	

}
