package com.taotao.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParamItem;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class ItemServiceImpl implements ItemService {
	@Value("${ITEM_INFO}")
	private String  ITEM_INFO;
	@Value("${BASE}")
	private String  BASE;
	@Value("${DESC}")
	private String  DESC;
	@Value("${PARAM}")
	private String  PARAM;
	@Value("${Expiry_TIME}")
	private int   Expiry_TIME;


	@Autowired
	private JedisClient jedisClient;

    @Autowired
	private JmsTemplate jmsTemplate;
    @Autowired
	private ActiveMQTopic topic;

	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
    
	@Override
	public TbItem geTbItemById(long itemId) {
        //从缓存中取数据
		try {
			//查询缓存
			String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":BASE");
			if (StringUtils.isNotBlank(json)) {
				//把json转换为java对象
				TbItem tbitem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbitem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem tbitem= tbItemMapper.getItemById(itemId);
        //从数据库中取数据
		try {
			jedisClient.set(ITEM_INFO+":"+itemId+BASE,JsonUtils.objectToJson(tbitem));
			//设置过期时间
			jedisClient.expire(ITEM_INFO+":"+itemId+BASE,Expiry_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbitem;
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
	public TaotaoResult addItem(TbItem item, String desc,String itemParams) {
		final long itemId=IDUtils.genItemId();
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
		//存入规格参数

		TbItemParamItem tbItemParamItem=new TbItemParamItem();
		tbItemParamItem.setItemId(itemId);
		tbItemParamItem.setParamData(itemParams);
		tbItemParamItem.setCreated(date);
		tbItemParamItem.setUpdated(date);
		tbItemParamItemMapper.insertTbitemParamItem(tbItemParamItem);



		jmsTemplate.send(topic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
                //发送id
				TextMessage textMwssage = session.createTextMessage(itemId+"");
				return textMwssage;
			}
		});


		return TaotaoResult.ok();
		

	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		//从缓存中取数据
		try {
			//查询缓存
			String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":DESC");
			if (StringUtils.isNotBlank(json)) {
				//把json转换为java对象
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemDesc tbItemDesc=tbItemDescMapper.getItemDescById(itemId);

		//从数据库中取数据
		try {
			jedisClient.set(ITEM_INFO+":"+itemId+DESC,JsonUtils.objectToJson(tbItemDesc));
			jedisClient.expire(ITEM_INFO+":"+itemId+DESC,Expiry_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return tbItemDesc;
	}

	//要展示规格参数的话应该是一个html页面
	//所以我们要在java代码里自己拼接html页面
	@Override
	public String getItemParamItemByItemId(long itemId) {
		TbItemParamItem itemParamItem = tbItemParamItemMapper.getItemParamItemByItemId(itemId);
		//这个是数据库中的json数据
		String paramData=itemParamItem.getParamData();
		//map (key=group value=param(key:k,walue:v))
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for(Map m1:jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for(Map m2:list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
				sb.append("            <td>"+m2.get("v")+"</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}


}
