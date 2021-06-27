package com.kend.backend.security;

import cn.hutool.core.util.StrUtil;
import com.kend.backend.entity.SysUser;
import com.kend.backend.service.ISysUserService;
import com.kend.backend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import com.kend.backend.common.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtils.getHeader());

        if(StrUtil.isBlankOrUndefined(jwt)){
            chain.doFilter(request,response);
            return;
        }

        Claims claims = jwtUtils.getClaimByToken(jwt);

        if(claims == null){
            jwtAuthenticationEntryPoint.commence(request,response, new JwtAuthenticationException("登录异常,请重新登录"));
        }
        if(jwtUtils.isTokenExpired(claims)){
            jwtAuthenticationEntryPoint.commence(request,response, new JwtAuthenticationException("登录时间过期，请重新登录"));
        }

        String username = claims.getSubject();

        SysUser user = sysUserService.getUserByUsername(username);
        //获取用户权限信息

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,null,userDetailsService.authorities(user.getId()));
        // 实行自动登录操作
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request,response);
    }
}
