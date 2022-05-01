package dev.appladostudios.examples.finalassignment.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("dev.applaudostudios.examples.finalassignment.persistence.repository.*")
@EntityScan("dev.applaudostudios.examples.finalassignment.persistence.model.*")
public class PersistenceConfiguration {

}
