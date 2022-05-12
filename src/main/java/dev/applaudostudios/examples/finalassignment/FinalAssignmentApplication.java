package dev.applaudostudios.examples.finalassignment;

import dev.applaudostudios.examples.finalassignment.config.KeycloakConfiguration;
import dev.applaudostudios.examples.finalassignment.config.SecurityConfiguration;
import dev.applaudostudios.examples.finalassignment.config.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("dev.applaudostudios.examples.finalassignment.*")
public class FinalAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class[]{FinalAssignmentApplication.class,
				SecurityConfiguration.class,
				KeycloakConfiguration.class,
				WebConfiguration.class}, args);
	}

}
