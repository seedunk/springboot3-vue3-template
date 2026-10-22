package com.seedunk.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
 

@Component
public class TemplateTask  implements SchedulingConfigurer {
 
       @Autowired
    private ObjectMapper objectMapper;
   

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
         
    }
    


}