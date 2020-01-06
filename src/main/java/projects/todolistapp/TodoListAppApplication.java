package projects.todolistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import projects.todolistapp.model.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class TodoListAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListAppApplication.class, args);
	}
}