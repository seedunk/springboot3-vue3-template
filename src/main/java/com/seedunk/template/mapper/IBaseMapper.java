package com.seedunk.template.mapper;
 
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

 

/***### ibatis映射器基类
 *    基类提供了一组数据库快速操作方法，有注入风险请谨慎使用
 */ 
public interface IBaseMapper<T> {
     
        /***#### 更新操作*/ 
        @Update("${command}")
        int updateCommand(@Param("command") String command );

        /***#### 插入操作*/ 
        @Insert("${command}")
        int insertCommand(@Param("command") String command );

        /***#### 查询列表*/ 
        @Select("${query}")
        List<Map<String, Object>> queryResult(@Param("query") String query );
         /***#### 查询列表且返回实体列表*/ 
        @Select("${query}")
        List<T> queryEntities(@Param("query") String query );
        /***#### 查询行数据*/ 
        @Select("${query}")
        Map<String, Object> queryRow(@Param("query") String query );

        /***#### 查询行数据且返回实体*/ 
        @Select("${query}")
        T queryEntity(@Param("query") String query );
}
