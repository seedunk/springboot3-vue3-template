package com.seedunk.template.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import org.springframework.http.MediaType;
@Configuration
public class FastJsonConfig {

    

     @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        // 关键：只处理 application/json，不要 MediaType.ALL
        List<MediaType> supported = new ArrayList<>();
        supported.add(MediaType.APPLICATION_JSON);
        supported.add(MediaType.parseMediaType("application/json;charset=UTF-8"));
        converter.setSupportedMediaTypes(supported);

        // 追加到默认列表，而不是替换
        return new HttpMessageConverters(true, List.of(converter));
    }

}