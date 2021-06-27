package com.kend.backend.security;

import cn.hutool.json.JSONUtil;
import com.kend.backend.common.lang.Result;
import com.kend.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 设置content类型
        response.setContentType("application/json;charset=utf-8");
        // 输出数据流
        ServletOutputStream outputStream = response.getOutputStream();

        //生成jwt， 并放置到请求头
        String jwt = jwtUtils.generateToken(authentication.getName());
        response.setHeader(jwtUtils.getHeader(),jwt);

        Result result = Result.succ(authentication.isAuthenticated());
        //将result 转成json格式并写进数据流
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        // 推送数据流，并在完成后关闭
        outputStream.flush();
        outputStream.close();
    }
}
