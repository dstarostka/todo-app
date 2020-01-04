package projects.todolistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import projects.todolistapp.config.JwtLoginFilter;
import projects.todolistapp.util.Mappings;

import java.util.Collections;

@SpringBootApplication
public class TodoListAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListAppApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new JwtLoginFilter());
		filterRegistrationBean.setUrlPatterns(Collections.singleton(Mappings.ITEMS));
		return filterRegistrationBean;
	}
}