package com.seedunk.template.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import com.seedunk.template.util.JwtUtil;

//@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // @Autowired
    // private AuthenticationManager authenticationManager;

    // @PostConstruct
    // public void init() {
    //     // 👇 把注入的 authenticationManager 传递给父类
    //     this.setAuthenticationManager(authenticationManager);
    // }

    

    // @Autowired
    // private JwtUtil jwtUtil;

    // // 覆盖默认的 /login 接口的 attemptAuthentication 方法
    // @Override
	// public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
    //     try {
    //         // 1. 从请求体中获取用户名和密码
    //         LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

    //         // 2. 创建未认证的 Authentication 对象
    //         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
    //                 loginRequest.getUsername(), loginRequest.getPassword());

    //         // 3. 交给 AuthenticationManager 进行认证
    //         return authenticationManager.authenticate(authenticationToken);
    //      }catch(AuthenticationException e1){

    //         System.getLogger(JwtAuthenticationFilter.class.getName()).log(System.Logger.Level.ERROR, (String) null, e1);
             
    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     } catch (Exception ex) {
    //         System.getLogger(JwtAuthenticationFilter.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    //     }
    //     return null;
    // }

    // // 认证成功后的回调
    // @Override
    // protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
    //                                         Authentication authResult) throws IOException, ServletException {
    //     // 1. 生成 JWT
    //     String jwt = jwtUtil.generateJwtToken(authResult);

    //     // 2. 将 JWT 返回给客户端 (例如放在 Response Body 或 Header 中)
    //     response.setContentType("application/json");
    //     response.getWriter().write("{\"token\": \"" + jwt + "\"}");
    // }
}