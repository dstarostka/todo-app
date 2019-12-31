package projects.todolistapp.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "projects.todolistapp")
public class ApplicationConfiguration implements WebMvcConfigurer {


}