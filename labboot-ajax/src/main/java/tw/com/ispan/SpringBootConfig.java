package tw.com.ispan;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBootConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ajax/pages/products/**")
                .allowedMethods("GET", "POST", "DELETE", "PUT");
        registry.addMapping("/ajax/secure/login");
    }
}
