package com.seedunk.template.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.seedunk.template.util.JwtUtil;

//@Component
public class TokenVerifyFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException  {
        
        //  String path = request.getRequestURI();
      
        // // ✅ 放行 WebSocket 路径
        // if (path.startsWith("/ws")) {
        //     filterChain.doFilter(request, response);

        //       //System.out.println("放行");
        //     return;
        // }
        try {
             
            
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                // 2. 从 JWT 中解析用户名
                String username = jwtUtil.getUserNameFromJwtToken(jwt);

                // 3. 根据用户名加载用户详细信息 (包括权限)
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 4. 创建已认证的 Authentication 对象，并放入 SecurityContextHolder
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
         
        
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        } 
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            String value=headerAuth.substring(7);
            return value.startsWith("DWV1")?null:value; 
        } 
        return null;
    }
}