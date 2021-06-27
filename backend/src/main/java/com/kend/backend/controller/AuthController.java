package com.kend.backend.controller;


import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.kend.backend.common.lang.Const;
import com.kend.backend.common.lang.Result;
import com.kend.backend.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
public class AuthController extends BaseController{

    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public Result createCaptcha() throws IOException {

        // 生成token
        String token = UUID.randomUUID().toString();
        // 生成验证码数据
        String captchaCode = producer.createText();

        // 测试代码
        token = "111";
        captchaCode = "222";


        /// 将验证码转换成图片
        BufferedImage image =producer.createImage(captchaCode);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

        // 将image解析成64位数据流
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        // 将验证码和token存储在redis
        redisUtil.hset(Const.CAPTCHA_KEY,token,captchaCode,120);
        return Result.succ(
                MapUtil.builder()
                        .put("token",token)
                        .put("base64Image",base64Img)
                        .build());

    }


    @GetMapping("/sys/userInfo")
    public Result userInfo(Principal principal){
       SysUser user =  sysUserService.getUserByUsername(principal.getName());


       return Result.succ(MapUtil
                            .builder()
                            .put("id",user.getId())
                            .put("avatar",user.getAvatar())
                            .put("username",user.getUsername())
                            .put("createdTime",user.getCreated())
                            .build()
        );
    }
}
