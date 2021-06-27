package com.kend.backend.security;

import cn.hutool.json.JSONUtil;
import com.kend.backend.common.lang.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        // 设置content类型
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 输出数据流
        ServletOutputStream outputStream = response.getOutputStream();

        Result result = Result.fail(exception.getMessage());
        //将result 转成json格式并写进数据流
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        // 推送数据流，并在完成后关闭
        outputStream.flush();
        outputStream.close();
    }

}
