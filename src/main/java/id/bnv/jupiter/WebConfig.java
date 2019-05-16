package id.bnv.jupiter;

import id.bnv.jupiter.authentication.Authentication;
import id.bnv.jupiter.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Authentication authentication;

    @Autowired
    public WebConfig(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new Interceptor(authentication));
    }
}
