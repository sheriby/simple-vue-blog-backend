package ink.sher.vueblog.config;

import ink.sher.vueblog.handler.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title WebConfig
 * @Package ink.sher.vueblog.config
 * @Description web config
 * @Author sher
 * @Date 2020/08/15 5:16 PM
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login");
    }
}
