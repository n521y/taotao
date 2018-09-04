package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

public class Item extends TbItem {
    public Item (){}

   //外界查询数据库 把数据库中的数据添加到tbItem对象中 然后将TbITEM对象属相赋值到Itemz
    public Item(TbItem tbItem) {
        this.setBarCode(tbItem.getBarCode());
        this.setCid(tbItem.getCid());
        this.setCreated(tbItem.getCreated());
        this.setId(tbItem.getId());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setStatus(tbItem.getStatus());
        this.setTitle(tbItem.getTitle());
        this.setUpdated(tbItem.getUpdated());
    }


    public String [] getImages(){
       if(super.getImage()!=null && !"".equals(super.getImage())){
           String [] strings=super.getImage().split(",");
           return strings;

       }
        return null;
    }

}
