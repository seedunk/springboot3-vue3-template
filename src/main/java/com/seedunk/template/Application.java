package com.seedunk.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
 
@SpringBootApplication
@EnableScheduling
public class  Application {
   
 
    public static void main(String[] args) {
            String[] customArgs = new String[]{
                                                "--spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/数据库?tcpKeepAlive=true",
                                                "--spring.datasource.username=账户",
                                                "--spring.datasource.password=密码"
                                                }; 
            SpringApplication.run(Application.class, customArgs);
    }
}
