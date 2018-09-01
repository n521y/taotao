package com.taotao.search.dao;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;

public interface SearchItemMapper {
	/**
	 * 
	 * 查询数据库 tbitem tbitemcat tbitemde
	 * @return
	 */
	List<SearchItem> getItemList();

}
