package ink.sher.vueblog.handler;

import ink.sher.vueblog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title LoginInterceptor
 * @Package ink.sher.vueblog.handler
 * @Description login interceptor
 * @Author sher
 * @Date 2020/08/15 5:13 PM
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "authorization,content-type");
        if ("GET".equals(request.getMethod())) {
           return true;
        }
        String token = request.getHeader("authorization");
        try {
            JwtUtils.verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
