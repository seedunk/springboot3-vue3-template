package com.seedunk.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebSocket
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
             registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(0xe10);
            }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // URL /static-org/**  ->  classpath:/static/org/
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static-vue/", "classpath:/static/")
                .setCachePeriod(3600*24*7);
 
    }
   // 配置 Vue Router 的路由转发
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
           // 只匹配一级 .html，且文件名不叫 index
            registry.addViewController("/{name:(?!index)[^\\.]*}.html")
                    .setViewName("forward:/index.html");
            // 二级：/user/profile.html
            registry.addViewController("/{p1:[^\\.]*}/{p2:(?!index)[^\\.]*}.html")
                    .setViewName("forward:/index.html");
    }
    // @Override
    // public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // // 追加到最前面，但不影响其他默认转换器
    //             StringHttpMessageConverter stringConverter =
    //                     new StringHttpMessageConverter(StandardCharsets.UTF_8);
    //             stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
    //             converters.add(0, stringConverter);

    //             // 2) 添加 Jackson JSON 转换器，支持 application/json;charset=UTF-8
    //                 // MappingJackson2HttpMessageConverter jsonConverter =
    //                 //         new MappingJackson2HttpMessageConverter();

    //                 // // 设置 ObjectMapper（可选：Spring Boot 已有一个，建议注入复用）
    //                 // ObjectMapper objectMapper = new ObjectMapper();
    //                 // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //                 // jsonConverter.setObjectMapper(objectMapper);

    //                 // // 设置支持的 MediaType，明确带 charset
    //                 // List<MediaType> mediaTypes = new ArrayList<>();
    //                 //         mediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
    //                 //         mediaTypes.add(new MediaType("application", "*+json", StandardCharsets.UTF_8));
    //                 // jsonConverter.setSupportedMediaTypes(mediaTypes); 
    //                 //converters.add(0, jsonConverter);
    //         }
                


        
    
}
