package dev.appladostudios.examples.finalassignment;

import dev.appladostudios.examples.finalassignment.config.KeycloakConfiguration;
import dev.appladostudios.examples.finalassignment.config.SecurityConfiguration;
import dev.appladostudios.examples.finalassignment.config.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan("dev.appladostudios.examples.finalassignment.*")
public class FinalAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class[]{FinalAssignmentApplication.class,
				SecurityConfiguration.class,
				KeycloakConfiguration.class,
				WebConfiguration.class}, args);
	}

}
