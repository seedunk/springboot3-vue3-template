package com.seedunk.template.config;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DeepSeekConfig  {
 

    @Bean
    public DeepSeekChatModel deepSeekChatModel() {
          
        ApiKey apiKey = new SimpleApiKey(System.getenv("DEEPSEEK_APIKEY"));

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

        DeepSeekApi deepSeekApi = new DeepSeekApi(
                "https://api.deepseek.com",           // baseUrl
                apiKey,                                // ApiKey
                headers,                               // headers
                "/chat/completions",                   // completionsPath
                "/embeddings",                         // embeddingsPath
                RestClient.builder(),                  // RestClient.Builder
                WebClient.builder(),                   // WebClient.Builder
                errorHandler                           // ResponseErrorHandler
        );

        return DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .build();
    }
}
