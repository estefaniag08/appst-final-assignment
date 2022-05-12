package dev.applaudostudios.examples.finalassignment.config;

import dev.applaudostudios.examples.finalassignment.persistence.query.OrderQueries;
import dev.applaudostudios.examples.finalassignment.persistence.query.UserQueries;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("dev.applaudostudios.examples.finalassignment.persistence.repository")
@EntityScan("dev.applaudostudios.examples.finalassignment.persistence.model")
public class PersistenceConfiguration {
    @Bean
    UserQueries userQueries(){
        return new UserQueries();
    }

    @Bean
    OrderQueries orderQueries(){
        return new OrderQueries();
    }
}
