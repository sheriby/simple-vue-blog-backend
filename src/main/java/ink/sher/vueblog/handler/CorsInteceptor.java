package ink.sher.vueblog.handler;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title CorsInteceptor
 * @Package ink.sher.vueblog.handler
 * @Description handler cross origin
 * @Author sher
 * @Date 2020/08/16 11:31 AM
 */
public class CorsInteceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "*");
        response.setHeader("Access-Control-Allow-Headers", "authorization,content-type");
        return true;
    }
}
