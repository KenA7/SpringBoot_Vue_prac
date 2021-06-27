package com.kend.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@Component
@ConfigurationProperties(prefix = "kend.jwt")
public class JwtUtils {

    private long expire; // 过期时间时长
    private String secret; //密钥
    private String header;

    //生成jwt
    public String generateToken(String username){


        Date nowDate = new Date(); //创建token的时间
        Date expiredDate = new Date(nowDate.getTime() + 1000 * expire); // Token过期日期


        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();

    }



    //解析jwt
    public Claims getClaimByToken(String jwt){


        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        }catch (Exception e){
            return null;
        }

    }


    //判断jwt是否过期
    public boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }

}
