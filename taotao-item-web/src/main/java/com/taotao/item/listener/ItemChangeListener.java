package com.taotao.item.listener;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ItemChangeListener implements MessageListener {
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    private Writer writer;
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage=(TextMessage)message;

            try {
                String itemId=textMessage.getText();
                TbItem tbItem = itemService.geTbItemById(Long.valueOf(itemId));
                Item item=new Item(tbItem);
                TbItemDesc itemDesc= itemService.getItemDescById(Long.valueOf(itemId));
                String itemParamItem= itemService.getItemParamItemByItemId(Long.valueOf(itemId));
                //通过Spring的freeMarkerConfigurer对象得到configuration
                Configuration configuration = freeMarkerConfigurer.getConfiguration();
                //传入的为模板名
                Template template = configuration.getTemplate("item.ftl");
                Map map=new HashMap();
                map.put("item",item);
                map.put("itemDesc",itemDesc);
                map.put("itemParamItem",itemParamItem);
                writer=new FileWriter(new File(HTML_OUT_PATH+itemId+".html"));
                template.process(map,writer);

            } catch (JMSException e) {
                e.printStackTrace();
            } catch (MalformedTemplateNameException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (TemplateNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }finally {
                if(writer!=null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
