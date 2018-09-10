<html>

    <head>
        <title>
            测试pojo
        </title>
    </head>
<body>
    <table border="1">
        <tr>
            <td>学生学号</td>
            <td>学生姓名</td>
            <td>学生年龄</td>
        </tr>
        <#list list as student>
        <!-- 隔行变色-->
        <#if student_index % 2==0>
            <tr bgcolor="red">
        <#else>
            <tr bgcolor="aqua">
        </#if>
               <td>${student.id}</td>
               <td>${student.name}</td>
               <td>${student.age}</td>
           </tr>
        </#list>
        <tr>
            <!-- Use ?date, 2018-9-5
                     ?time 10:22:30
                     ?datetime 2018-9-5 10:23:12
                     ?string(pattern), like ?string('dd.MM.yyyy HH:mm:ss') 自定义日期格式
             .-->
            <td>日期格式：</td>
            <td>${date?datetime}</td>
        </tr>
        <tr>
            <!--
                null处理  ??判断该值是否为空

            -->
            <td>null处理</td>
            <#if val??>
                <td>该值不为null</td>
              <#else>
                  <td>该值为null</td>
            </#if>
        </tr>
    </table>
    <#include "demo1.ftl">

</body>

</html>