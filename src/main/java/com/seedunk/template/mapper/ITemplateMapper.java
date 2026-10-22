package com.seedunk.template.mapper;
 
import org.apache.ibatis.annotations.Mapper;

import com.seedunk.template.entity.Template;

/***### ibatis映射器示例 
 *   映射器示例继承映射器基类 
 */ 
@Mapper
public interface ITemplateMapper extends  IBaseMapper<Template> {

    
 
}
