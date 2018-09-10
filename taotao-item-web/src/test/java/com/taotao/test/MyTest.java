package com.taotao.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class MyTest {
    /**
     *
     * map类型
     * @throws Exception
     */
    @Test
    public void demo1() throws Exception {
        //自动去寻找freemaker的版本
        Configuration configuration= new Configuration(Configuration.getVersion());
        //目前我们设置本地路径
        configuration.setDirectoryForTemplateLoading(new File("F:\\jindong\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //设置字符编码
        configuration.setDefaultEncoding("utf-8");
        //模板对象
        Template template =configuration.getTemplate("demo1.ftl");
        Map map=new HashMap();
        map.put("hello","欢迎牛凉意");
        //告诉我们静态页面 输出到哪里
        Writer writer=new FileWriter(new File("F:\\abc.txt"));
        //第一个是数据  第二个是输出的地址
        template.process(map,writer);
        writer.close();


    }

    /**
     * pojo类型
     * @throws Exception
     */
    @Test
    public void demo2() throws Exception {
        //自动去寻找freemaker的版本
        Configuration configuration= new Configuration(Configuration.getVersion());
        //目前我们设置本地路径
        configuration.setDirectoryForTemplateLoading(new File("F:\\jindong\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //设置字符编码
        configuration.setDefaultEncoding("utf-8");
        //模板对象
        Template template =configuration.getTemplate("demo2.ftl");
        Student student=new Student(111,"牛亮艺",24);
        Map map=new HashMap();
        map.put("student",student);
        //告诉我们静态页面 输出到哪里
        Writer writer=new FileWriter(new File("F:\\aaa.html"));
        //第一个是数据  第二个是输出的地址
        template.process(map,writer);
        writer.close();


    }

    /**
     * 集合类型
     *
     */
    @Test
    public void demo3() throws Exception {
        //自动去寻找freemaker的版本
        Configuration configuration= new Configuration(Configuration.getVersion());
        //目前我们设置本地路径
        configuration.setDirectoryForTemplateLoading(new File("F:\\jindong\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //设置字符编码
        configuration.setDefaultEncoding("utf-8");
        //模板对象
        Template template =configuration.getTemplate("demo3.ftl");
        List list=new ArrayList<Student>();
        Student student1=new Student(111,"牛亮艺",24);
        Student student2=new Student(222,"牛文凯",23);
        Student student3=new Student(333,"牛石头",22);
        list.add(student1);
        list.add(student2);
        list.add(student3);
        Map map=new HashMap();
        map.put("date",new Date());
        map.put("list",list);
        //注意  虽然在模板中 include了demo1。ftl 但是他还需要再这里引这个 hello 数据层必须有这个hello
        map.put("hello","欢迎萌芽");
        //告诉我们静态页面 输出到哪里
        Writer writer=new FileWriter(new File("F:\\bbb.html"));
        //第一个是数据  第二个是输出的地址
        template.process(map,writer);
        writer.close();
    }


}
