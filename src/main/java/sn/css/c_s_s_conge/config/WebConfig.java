package sn.css.c_s_s_conge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dmt/**")
            .addResourceLocations("classpath:/static/dmt/")
            .setCachePeriod(0); // désactiver le cache
    }

//    http://localhost:8080/dmt/
//    Fah Voice.png

}
