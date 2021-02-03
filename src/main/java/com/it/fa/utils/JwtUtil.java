package com.it.fa.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.it.fa.model.User;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Data
public class JwtUtil {
    public static final String SIGN = "@q$w^e*r!!!";
    public static final Integer TIMEOUT = 7;
    public static String generateToken(User user){
        Map map = new HashMap<String,Object>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,TIMEOUT);
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withExpiresAt(calendar.getTime())
                .withHeader(map)
                .withClaim("uid", user.getUid())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256(SIGN));
        return token;
    }
    public static DecodedJWT verifyToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        return verify;
    }
}
