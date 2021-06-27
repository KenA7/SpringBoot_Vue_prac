package com.kend.backend.security;

import cn.hutool.json.JSONUtil;
import com.kend.backend.common.exception.JwtAuthenticationException;
import com.kend.backend.common.lang.Result;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 设置content类型
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 输出数据流
        ServletOutputStream outputStream = response.getOutputStream();

//        if(exception.getClass() == JwtAuthenticationException.class){
//            Result result = Result.fail(exception.getMessage());
//            //将result 转成json格式并写进数据流
//            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
//            // 推送数据流，并在完成后关闭
//            outputStream.flush();
//            outputStream.close();
//        }
        Result result = Result.fail("尚未登录，请登录");
        //将result 转成json格式并写进数据流
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        // 推送数据流，并在完成后关闭
        outputStream.flush();
        outputStream.close();
    }
    }
