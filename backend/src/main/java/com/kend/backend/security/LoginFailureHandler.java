package com.kend.backend.security;

import cn.hutool.json.JSONUtil;
import com.kend.backend.common.exception.CaptchaException;
import com.kend.backend.common.lang.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private  Result result;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 设置content类型
        response.setContentType("application/json;charset=utf-8");
        // 输出数据流
        ServletOutputStream outputStream = response.getOutputStream();

        // 创建返回数据

        if(exception.getClass()== CaptchaException.class){
            result = Result.fail(exception.getMessage());
        }else{
            result = Result.fail("用户名或者密码错误");
        }
        //将result 转成json格式并写进数据流
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        // 推送数据流，并在完成后关闭
        outputStream.flush();
        outputStream.close();
    }
}
