package dev.appladostudios.examples.finalassignment.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("dev.appladostudios.examples.finalassignment.persistence.repository")
@EntityScan("dev.appladostudios.examples.finalassignment.persistence.model")
public class PersistenceConfiguration {

}
