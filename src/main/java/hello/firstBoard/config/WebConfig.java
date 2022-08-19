package hello.firstBoard.config;

import hello.firstBoard.debug.loginDelayFinder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new loginDelayFinder())
                .order(1)
                .addPathPatterns("/login")
                .excludePathPatterns("/css/**","/*.ico","/error");
    }
}
