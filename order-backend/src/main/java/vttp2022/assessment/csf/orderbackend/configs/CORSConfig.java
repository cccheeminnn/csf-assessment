package vttp2022.assessment.csf.orderbackend.configs;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CORSConfig implements WebMvcConfigurer{

    private String path;
    private String origins;

    public CORSConfig (String p, String o) {
        path = p;
        origins = o;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(path).allowedOrigins(origins);
    }
    
}
