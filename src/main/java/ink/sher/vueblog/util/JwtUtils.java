package ink.sher.vueblog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @Title JwtUtils
 * @Package ink.sher.vueblog.util
 * @Description jwt utils
 * @Author sher
 * @Date 2020/08/15 4:22 PM
 */
public class JwtUtils {

    private static final String SIGN = "@#$dsjlksafASFds&^!fds";

    public static String getToken(Map<String, String> map) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        return builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SIGN));
    }

    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
}
