package com.seedunk.template.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RestController;

import com.seedunk.template.common.ObjectResponse; 
import com.seedunk.template.entity.Template;
import com.seedunk.template.mapper.ITemplateMapper;

import jakarta.validation.Valid;

@RestController 
public class TemplateController{
    

   
    @Autowired
    private ITemplateMapper templateMapper;
     
     
  
    @GetMapping("init.json")
    public  ObjectResponse getTemplate() {

         return new ObjectResponse().build(); 
    }
    // @GetMapping("/**")
    // public  ModelAndView  index(HttpServletRequest request) {
    
    //     String[] args= request.getRequestURI().split( "/template/");   
    //     String source= args.length>1?args[1]:"404.html";
    //     ServletContext ctx = request.getServletContext();
     
    //     if (ctx.getResourceAsStream(source) == null) {
        
    //         ModelAndView mv = new ModelAndView("error/notFound");
    //         mv.addObject("message", "文件不存在");
    //         return mv;
    //     }else return new ModelAndView("forward:/static-vue/"+source);  
    // }
 
    
    // public  ObjectResponse state(@Valid @RequestBody Map<String, Object> params) {
    @PostMapping("registration.json")
    public  ObjectResponse postTemplate(@Valid @RequestBody Template template) {
              ObjectResponse response=new ObjectResponse(); 
               
                    
            //   }else{
             return  response.build("注册信息有误");
              
    }
    
      
}
