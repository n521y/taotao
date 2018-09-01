package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbItemCat;

public interface TbItemCatMapper {
   List<TbItemCat> geTbItemCatsById(long parentId);
}