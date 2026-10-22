package com.seedunk.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.seedunk.template.security.JwtAuthenticationFilter;
import com.seedunk.template.security.TokenVerifyFilter;
 

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 

 
    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    @Lazy
    private TokenVerifyFilter tokenVerifyFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 临时关闭 CSRF
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // 临时关闭所有权限验证：全部放行
                .anyRequest().permitAll()
            );
            // 临时关闭自定义过滤器
            // .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // .addFilterBefore(tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
   // @Bean 
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
            http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/ws/**", "/api/device/**","/org/**")) //.disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                /**[公开资源](#marker) */
                .requestMatchers("/api/auth/**", "/",   "/index.html", "/css/**", "/js/**", "/assets/**", "/api/device/**","/ws/**","/org/**").permitAll()
                /**[需要权限](#marker) */
                .requestMatchers("/api/reports/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/api/sop/**").hasAnyRole("ADMIN", "OPERATOR", "MANAGER")  
                
                /**[需要权限](#marker) */
                .anyRequest().authenticated()
                )
                
                // .oauth2ResourceServer(oauth2 -> oauth2
                // .jwt(jwt -> jwt
                //     .jwtAuthenticationConverter(jwtAuthenticationConverter()) // 可选：自定义权限转换
                // )
                 //)
            // 将我们自定义的登录过滤器配置到 /api/auth/login 路径
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // 将我们的授权过滤器加到 UsernamePasswordAuthenticationFilter 之前
            .addFilterBefore(tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

     // 自定义 JWT 中的权限映射
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_"); // 设置权限前缀

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }


      @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("用户名/密码认证未启用，请使用 JWT");
        };
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());  // 👈 直接调用方法获取 Bean
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}