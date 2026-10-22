package com.seedunk.template.tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
 
 
import com.seedunk.template.mapper.ITemplateMapper;

@Component
public class TemplateTool {
    
     @Autowired
    private ITemplateMapper templateMapper;
    
    @Tool(description = "获取示例信息")
    public String getProductionData(
            @ToolParam(description = "操作工名字，例如：方世玉") String username,
            @ToolParam(description = "开始日期，格式 YYYY-MM-DD") String startDate,
            @ToolParam(description = "结束日期，格式 YYYY-MM-DD") String endDate) {
        // 模拟查询 MES 或数据库

        // var member =templateMapper.queryEntity("select * from newcloud_member where display_name='"+username+"'");
        // if(member==null){
        //    return """ 
        //           这不是我们的员工
        //           """;
        // }else{

        //     List<Map<String, Object>> result=  memberMapper.queryResult(" select count(0) as 总件数,sum(device_time_cost) as 总工时,ROUND(AVG(device_time_cost), 1) AS 平均耗时 from newcloud_record   where device_created_at>='"
        //         +startDate+" 00:00:00' and device_created_at<='"+endDate+" 23:59:59' and op="+member.getMemberId());

        //      try {
        //         return "需要数据中时间单位秒转换成合适的表示方式，" + objectMapper.writeValueAsString(result);
        //     } catch (JsonProcessingException e) {
        //          return "";
        //     }
            
        // }
        return "";
      
    }


    @Tool(description = "获取某地区的天气情况")
    public String cleanAlrm(
            @ToolParam(description = "地区名称，例如：常州市、金坛区、神奈川、芝加哥") String name  ) {
          
            return "消息以发送至管理员";
         
      
    }



    @Tool(description = "获取今天的日期、获取当天的日期、获取本日的日期")
    public String day() { 
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

     @Tool(description = "获取当前的时间")
    public String now() { 
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
