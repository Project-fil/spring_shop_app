package com.github.ratel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@OpenAPIDefinition(
        servers = @Server(
                url = "http://localhost:8083/",
                description = "localhost"
        )
)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.github.ratel.repositories")
@EntityScan(basePackages = "com.github.ratel.entity")
public class AppConfig {

//    @Bean
//    public JavaMailSenderImpl mailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//    }

}
