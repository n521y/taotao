package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SolrServer solrServer;


	@Override
	public TaotaoResult importAllItems(){
		       try {
				// 1、查询所有商品数据。
					List<SearchItem> itemList = searchItemMapper.getItemList();
					// 2、创建一个SolrServer对象。
					// 3、为每个商品创建一个SolrInputDocument对象。
					for (SearchItem searchItem : itemList) {
						SolrInputDocument document = new SolrInputDocument();
						// 4、为文档添加域
						document.addField("id", searchItem.getId());
						document.addField("item_title", searchItem.getTitle());
						document.addField("item_sell_point", searchItem.getSell_point());
						document.addField("item_price", searchItem.getPrice());
						document.addField("item_image", searchItem.getImage());
						document.addField("item_category_name", searchItem.getCategory_name());
						document.addField("item_desc", searchItem.getItem_desc());
						// 5、向索引库中添加文档。
						solrServer.add(document);
					}
					//提交修改
					solrServer.commit();
					// 6、返回TaotaoResult。
					return TaotaoResult.ok();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
             return TaotaoResult.build(500, "报错了");
	}

	@Override
	public TaotaoResult addDocument(long itemId) {
		// 1、根据商品id查询商品信息。
		SearchItem searchItem = searchItemMapper.getItemById(itemId);
		// 2、创建一SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 3、使用SolrServer对象写入索引库。
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		document.addField("item_desc", searchItem.getItem_desc());
		// 5、向索引库中添加文档。
		try {
			solrServer.add(document);
			solrServer.commit();
			return TaotaoResult.ok();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 4、返回成功，返回TaotaoResult。

		return null;
	}

}
