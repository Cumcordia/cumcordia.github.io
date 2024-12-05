package kz.goldenfish.goldenfish.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry
                .addResourceHandler("/static/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry
                .addResourceHandler("/static/img/**")
                .addResourceLocations("classpath:/static/img/");
        registry
                .addResourceHandler("/static/videos/**")
                .addResourceLocations("classpath:/static/videos/");
        registry
                .addResourceHandler("/static/svg/**")
                .addResourceLocations("classpath:/static/svg/");
    }
}