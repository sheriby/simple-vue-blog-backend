package ink.sher.vueblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title MpConfig
 * @Package ink.sher.vueblog.config
 * @Description mybatis plus config
 * @Author sher
 * @Date 2020/08/08 6:12 PM
 */
@Configuration
@MapperScan(basePackages = "ink.sher.vueblog.mapper")
public class MpConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
